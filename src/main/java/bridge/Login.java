package autenticacao;

// Bridge: abstração que delega ao mecanismo concreto
public abstract class Login {

    protected MecanismoAuth mecanismo;

    public Login(MecanismoAuth mecanismo) {
        this.mecanismo = mecanismo;
    }

    public abstract boolean entrar(String usuario);

    public abstract String getDescricao();
}
