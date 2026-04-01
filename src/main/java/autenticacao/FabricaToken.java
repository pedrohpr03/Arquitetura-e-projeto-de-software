package autenticacao;

public class FabricaToken implements FabricaAutenticacao {

    @Override
    public MecanismoAuth criarMecanismo() {
        return new MecanismoToken();
    }
}