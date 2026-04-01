package autenticacao;

public class MecanismoBiometria implements MecanismoAuth {

    @Override
    public boolean autenticar(String usuario) {
        System.out.println("Lendo biometria do usuário: " + usuario);
        return usuario != null && usuario.length() >= 3;
    }

    @Override
    public String getNome() {
        return "Biometria";
    }
}