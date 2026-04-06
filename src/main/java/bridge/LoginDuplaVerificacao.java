package autenticacao;

// Bridge: refinamento da abstração para login com dois fatores
public class LoginDuplaVerificacao extends Login {

    private final MecanismoAuth segundoFator;

    public LoginDuplaVerificacao(MecanismoAuth primeiroFator, MecanismoAuth segundoFator) {
        super(primeiroFator);
        this.segundoFator = segundoFator;
    }

    @Override
    public boolean entrar(String usuario) {
        System.out.println("[2FA] 1º fator: " + mecanismo.getNome());
        boolean primeiro = mecanismo.autenticar(usuario);
        if (!primeiro) return false;

        System.out.println("[2FA] 2º fator: " + segundoFator.getNome());
        return segundoFator.autenticar(usuario);
    }

    @Override
    public String getDescricao() {
        return "Login com dupla verificação: " + mecanismo.getNome() + " + " + segundoFator.getNome();
    }
}
