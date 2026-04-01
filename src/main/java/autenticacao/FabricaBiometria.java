package autenticacao;

public class FabricaBiometria implements FabricaAutenticacao {

    @Override
    public MecanismoAuth criarMecanismo() {
        return new MecanismoBiometria();
    }
}