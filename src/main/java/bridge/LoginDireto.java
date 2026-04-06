package autenticacao;

// Bridge: refinamento da abstração para login com fator único
public class LoginDireto extends Login {

    public LoginDireto(MecanismoAuth mecanismo) {
        super(mecanismo);
    }

    @Override
    public boolean entrar(String usuario) {
        System.out.println("[Login direto] usando: " + mecanismo.getNome());
        return mecanismo.autenticar(usuario);
    }

    @Override
    public String getDescricao() {
        return "Login direto via " + mecanismo.getNome();
    }
}
