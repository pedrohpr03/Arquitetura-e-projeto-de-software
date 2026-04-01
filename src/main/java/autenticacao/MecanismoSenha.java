package autenticacao;

public class MecanismoSenha implements MecanismoAuth {

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