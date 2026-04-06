package autenticacao;

// Bridge: implementação concreta do mecanismo por token OTP
public class VerificacaoToken implements MecanismoAuth {

    @Override
    public boolean autenticar(String usuario) {
        System.out.println("Validando token OTP do usuário: " + usuario);
        return usuario != null && !usuario.isBlank();
    }

    @Override
    public String getNome() {
        return "Token OTP";
    }
}
