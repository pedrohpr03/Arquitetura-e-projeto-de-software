package autenticacao.provedor;

import autenticacao.Login;
import autenticacao.MecanismoAuth;

// Abstract Factory: contrato para criação de famílias de objetos por provedor
public interface ConfiguracaoProvedor {
    MecanismoAuth criarMecanismoPrimario();
    MecanismoAuth criarMecanismoSecundario();
    Login criarLogin();
    String getNome();
}
