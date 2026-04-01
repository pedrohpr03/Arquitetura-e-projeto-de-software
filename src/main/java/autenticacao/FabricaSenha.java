package autenticacao;

public class FabricaSenha implements FabricaAutenticacao {

    @Override
    public MecanismoAuth criarMecanismo() {
        return new MecanismoSenha();
    }
}