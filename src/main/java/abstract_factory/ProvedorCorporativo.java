package autenticacao.provedor;

import autenticacao.Login;
import autenticacao.LoginDuplaVerificacao;
import autenticacao.MecanismoAuth;
import autenticacao.VerificacaoSenha;
import autenticacao.VerificacaoToken;

// Abstract Factory: família corporativa — Senha + Token + dupla verificação
public class ProvedorCorporativo implements ConfiguracaoProvedor {

    @Override
    public MecanismoAuth criarMecanismoPrimario() {
        return new VerificacaoSenha();
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
        return "Corporativo";
    }
}
