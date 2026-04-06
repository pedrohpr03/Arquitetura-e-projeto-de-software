package autenticacao;

// Factory Method: criação concreta do mecanismo biométrico
public class CriadorBiometria implements CriadorMecanismo {

    @Override
    public MecanismoAuth criar() {
        return new VerificacaoBiometrica();
    }
}
