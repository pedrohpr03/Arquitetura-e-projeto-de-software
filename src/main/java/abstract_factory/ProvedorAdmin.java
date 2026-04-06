package autenticacao.provedor;

import autenticacao.Login;
import autenticacao.LoginDuplaVerificacao;
import autenticacao.MecanismoAuth;
import autenticacao.VerificacaoBiometrica;
import autenticacao.VerificacaoToken;

// Abstract Factory: família admin — Biometria + Token + dupla verificação
public class ProvedorAdmin implements ConfiguracaoProvedor {

    @Override
    public MecanismoAuth criarMecanismoPrimario() {
        return new VerificacaoBiometrica();
    }

    @Override
    public MecanismoAuth criarMecanismoSecundario() {
        return new VerificacaoToken();
    }

    @Override
    public Login criarLogin() {
        return new LoginDuplaVerificacao(criarMecanismoPrimario(), criarMecanismoSecundario());
    }

    @Override
    public String getNome() {
        return "Admin";
    }
}
