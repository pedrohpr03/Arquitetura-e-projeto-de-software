package autenticacao;

// Bridge: implementação concreta do mecanismo por senha
public class VerificacaoSenha implements MecanismoAuth {

    @Override
    public boolean autenticar(String usuario) {
        System.out.println("Verificando senha do usuário: " + usuario);
        return usuario != null && !usuario.isBlank();
    }

    @Override
    public String getNome() {
        return "Senha";
    }
}
