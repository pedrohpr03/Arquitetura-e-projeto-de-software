package autenticacao;

import autenticacao.provedor.ConfiguracaoProvedor;
import autenticacao.sessao.GerenciadorSessao;

public class ServicoAutenticacao {

    private final ConfiguracaoProvedor provedor;
    private final GerenciadorSessao gerenciadorSessao;

    public ServicoAutenticacao(ConfiguracaoProvedor provedor) {
        this.provedor = provedor;
        this.gerenciadorSessao = GerenciadorSessao.getInstance();
    }

    public boolean autenticar(String usuario) {
        System.out.println("\n=== Provedor: " + provedor.getNome() + " ===");

        Login login = provedor.criarLogin();
        System.out.println("Estratégia: " + login.getDescricao());

        boolean resultado = login.entrar(usuario);

        if (resultado) {
            gerenciadorSessao.registrarSessao(usuario);
        } else {
            System.out.println("[Autenticação] Falha para: " + usuario);
        }

        return resultado;
    }

    public boolean temSessaoAtiva(String usuario) {
        return gerenciadorSessao.temSessaoAtiva(usuario);
    }

    public void encerrarSessao(String usuario) {
        gerenciadorSessao.encerrarSessao(usuario);
    }
}
