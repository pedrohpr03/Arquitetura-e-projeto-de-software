package autenticacao;

public class LoginMultifator extends AutenticacaoAbstrata {

    private final MecanismoAuth segundoFator;

    public LoginMultifator(MecanismoAuth primeiroFator, MecanismoAuth segundoFator) {
        super(primeiroFator);
        this.segundoFator = segundoFator;
    }

    @Override
    public boolean login(String usuario) {
        System.out.println("[MFA] 1º fator: " + mecanismo.getNome());
        boolean primeiro = mecanismo.autenticar(usuario);
        if (!primeiro) return false;

        System.out.println("[MFA] 2º fator: " + segundoFator.getNome());
        return segundoFator.autenticar(usuario);
    }

    @Override
    public String getDescricao() {
        return "Login multifator: " + mecanismo.getNome() + " + " + segundoFator.getNome();
    }
}