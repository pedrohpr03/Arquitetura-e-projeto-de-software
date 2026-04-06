package autenticacao;

// Factory Method: criação concreta do mecanismo de senha
public class CriadorSenha implements CriadorMecanismo {

    @Override
    public MecanismoAuth criar() {
        return new VerificacaoSenha();
    }
}
