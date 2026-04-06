package autenticacao.provedor;

import autenticacao.Login;
import autenticacao.LoginDireto;
import autenticacao.MecanismoAuth;
import autenticacao.VerificacaoToken;

// Abstract Factory: família social — Token + login direto
public class ProvedorSocial implements ConfiguracaoProvedor {

    @Override
    public MecanismoAuth criarMecanismoPrimario() {
        return new VerificacaoToken();
    }

    @Override
    public MecanismoAuth criarMecanismoSecundario() {
        return new VerificacaoToken();
    }

    @Override
    public Login criarLogin() {
        return new LoginDireto(criarMecanismoPrimario());
    }

    @Override
    public String getNome() {
        return "Social";
    }
}
