package autenticacao;

public class MecanismoToken implements MecanismoAuth {

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