package autenticacao;

// Factory Method: criação concreta do mecanismo de token OTP
public class CriadorToken implements CriadorMecanismo {

    @Override
    public MecanismoAuth criar() {
        return new VerificacaoToken();
    }
}
