package autenticacao;

public class LoginSimples extends AutenticacaoAbstrata {

    public LoginSimples(MecanismoAuth mecanismo) {
        super(mecanismo);
    }

    @Override
    public boolean login(String usuario) {
        System.out.println("[Login simples] usando: " + mecanismo.getNome());
        return mecanismo.autenticar(usuario);
    }

    @Override
    public String getDescricao() {
        return "Login simples via " + mecanismo.getNome();
    }
}