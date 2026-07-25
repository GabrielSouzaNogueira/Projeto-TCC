package by.gabriel.gerenciadorEstoque.Services;

import by.gabriel.gerenciadorEstoque.Api.DTO.FormPagDTO.Consultas.SelectFormPagStatusDTO;
import by.gabriel.gerenciadorEstoque.Api.DTO.FormPagDTO.FormPagDTO;
import by.gabriel.gerenciadorEstoque.Api.DTO.FormPagDTO.UpdateFormPagDTO;
import by.gabriel.gerenciadorEstoque.Domain.Exception.User.UserLogadoNotNull;
import by.gabriel.gerenciadorEstoque.Domain.Exception.User.UserNotFoundException;
import by.gabriel.gerenciadorEstoque.Domain.Exception.User.UserNotPermission;
import by.gabriel.gerenciadorEstoque.Domain.ExceptionFormPag.FormPagAlreadyExistException;
import by.gabriel.gerenciadorEstoque.Domain.ExceptionFormPag.FormPagNotExistException;
import by.gabriel.gerenciadorEstoque.Domain.ExceptionFormPag.FormPagNotNullException;
import by.gabriel.gerenciadorEstoque.Domain.Model.FormaPag.FormaPagto;
import by.gabriel.gerenciadorEstoque.Domain.Model.Usuario.Usuario;
import by.gabriel.gerenciadorEstoque.Enum.FormaPag.FormaPagStatus;
import by.gabriel.gerenciadorEstoque.Enum.Usuario.UserCargo;
import by.gabriel.gerenciadorEstoque.Repository.FormPagRespository.FormPagRepository;
import by.gabriel.gerenciadorEstoque.Repository.Usuario.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormPagService {

    private final FormPagRepository formPagRepository;
    private final UserRepository userRepository;

    public FormPagService(FormPagRepository formPagRepository, UserRepository userRepository) {
        this.formPagRepository = formPagRepository;
        this.userRepository = userRepository;
    }

    public List<SelectFormPagStatusDTO> listar() {

        List<SelectFormPagStatusDTO> formaPagtos = formPagRepository.findByStatusCustom(FormaPagStatus.ATIVO);

        if (formaPagtos.isEmpty()) {
            throw new FormPagNotExistException("Nenhuma forma de pagamento existente no sistema!");

        }

        return formaPagtos;

    }

    @Transactional
    public FormaPagto cadastro(FormPagDTO dto, String usuarioLogado) {

        Usuario userLogado = userRepository.findByNomeIgnoreCase(usuarioLogado)
                .orElseThrow(() -> new UserNotFoundException("Usuario de nome: " + usuarioLogado + " Não foi encontrado"));


        if (userLogado.getUserCargo() != null && userLogado.getUserCargo() != UserCargo.ADMINISTRADOR && userLogado.getUserCargo() != UserCargo.DEV) {
            throw new UserNotPermission("Usuario não possui permissão para realizar está ação");
        }

        if (dto.descricao() == null || dto.descricao().isBlank()) {
            throw new FormPagNotNullException("A descrição não pode estar vazia!");
        }

        if (formPagRepository.findByDescricaoIgnoreCase(dto.descricao()).isPresent()) {
            throw new FormPagAlreadyExistException("Forma de pagamento já existente no sistema!");
        }

        FormaPagto formaPagto = new FormaPagto(
                dto.descricao().toUpperCase(),
                FormaPagStatus.ATIVO);

        formPagRepository.save(formaPagto);
        return formaPagto;
    }

    @Transactional
    public Boolean atualizar(Long id, UpdateFormPagDTO dto, String usuarioLogado) {

        FormaPagto formaPagto;

        if (usuarioLogado == null || usuarioLogado.isBlank()) {
            throw new UserLogadoNotNull("Header de usuario no cabeçalho foi enviado como null ou vazio!");
        }

        Usuario userLogado = userRepository.findByNomeIgnoreCase(usuarioLogado)
                .orElseThrow(() -> new UserNotFoundException("Usuario de nome: " + usuarioLogado + " Não foi encontrado"));

        if (userLogado.getUserCargo() != null && userLogado.getUserCargo() != UserCargo.ADMINISTRADOR && userLogado.getUserCargo() != UserCargo.DEV) {

            throw new UserNotPermission("Usuario não possui permissão para realizar está ação");
        }

        formaPagto = formPagRepository.findById(id)
                .orElseThrow(() -> new FormPagNotExistException("Forma de pagamento não encontrada no sistema!"));


        if(dto.descricao() != null && !dto.descricao().isBlank()) {

            if(formPagRepository.findByDescricaoIgnoreCase(dto.descricao()).isPresent()){
                throw new FormPagAlreadyExistException("Forma de pagamento já existente no sistema!");
            }

            formaPagto.setDescricao(dto.descricao().toUpperCase());
            formPagRepository.save(formaPagto);
        }

        return true;
    }

    @Transactional
    public Boolean delecao(Long id, String usuarioLogado) {

        FormaPagto formaPagto;

        if (usuarioLogado == null || usuarioLogado.isBlank()) {
            throw new UserLogadoNotNull("Header de usuario no cabeçalho foi enviado como null ou vazio!");
        }

        Usuario userLogado = userRepository.findByNomeIgnoreCase(usuarioLogado)
                .orElseThrow(() -> new UserNotFoundException("Usuario de nome: " + usuarioLogado + " Não foi encontrado"));

        if (userLogado.getUserCargo() != null && userLogado.getUserCargo() != UserCargo.ADMINISTRADOR && userLogado.getUserCargo() != UserCargo.DEV) {

            throw new UserNotPermission("Usuario não possui permissão para realizar está ação");
        }

        formaPagto = formPagRepository.findById(id).orElseThrow(() -> new FormPagNotExistException("Forma de pagamento com id: " + id +" não foi encontrado"));

        formaPagto.setStatus(FormaPagStatus.DESATIVADO);
        formPagRepository.save(formaPagto);

        System.out.println("Forma de pagamento excluida com sucesso");
        return true;
    }

}
