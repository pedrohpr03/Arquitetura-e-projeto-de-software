package autenticacao;

public abstract class AutenticacaoAbstrata {

    protected MecanismoAuth mecanismo;

    public AutenticacaoAbstrata(MecanismoAuth mecanismo) {
        this.mecanismo = mecanismo;
    }

    public abstract boolean login(String usuario);

    public abstract String getDescricao();
}