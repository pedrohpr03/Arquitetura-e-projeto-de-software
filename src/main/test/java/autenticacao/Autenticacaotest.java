package autenticacao;

import autenticacao.provedor.ProvedorAdmin;
import autenticacao.provedor.ProvedorCorporativo;
import autenticacao.provedor.ProvedorSocial;
import autenticacao.sessao.GerenciadorSessao;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class AutenticacaoTest {

    // ===================== BRIDGE =====================

    @Test
    @DisplayName("Bridge: login direto com senha deve autenticar usuário válido")
    void testLoginDiretoComSenha() {
        Login login = new LoginDireto(new VerificacaoSenha());
        assertTrue(login.entrar("joao"));
    }

    @Test
    @DisplayName("Bridge: login direto com usuário em branco deve falhar")
    void testLoginDiretoUsuarioBranco() {
        Login login = new LoginDireto(new VerificacaoSenha());
        assertFalse(login.entrar("  "));
    }

    @Test
    @DisplayName("Bridge: login direto com usuário nulo deve falhar")
    void testLoginDiretoUsuarioNulo() {
        Login login = new LoginDireto(new VerificacaoToken());
        assertFalse(login.entrar(null));
    }

    @Test
    @DisplayName("Bridge: biometria exige usuário com 3 ou mais caracteres")
    void testBiometriaUsuarioCurto() {
        Login login = new LoginDireto(new VerificacaoBiometrica());
        assertFalse(login.entrar("ab"));
        assertTrue(login.entrar("ana"));
    }

    @Test
    @DisplayName("Bridge: mesmo tipo de login com mecanismos diferentes retorna descrições distintas")
    void testBridgeDescricoesDistintas() {
        Login comSenha     = new LoginDireto(new VerificacaoSenha());
        Login comBiometria = new LoginDireto(new VerificacaoBiometrica());
        assertNotEquals(comSenha.getDescricao(), comBiometria.getDescricao());
    }

    @Test
    @DisplayName("Bridge: getDescricao deve conter o nome do mecanismo")
    void testDescricaoContemMecanismo() {
        Login login = new LoginDireto(new VerificacaoToken());
        assertTrue(login.getDescricao().contains("Token OTP"));
    }

    @Test
    @DisplayName("Bridge: dupla verificação com ambos os fatores válidos deve autenticar")
    void testDuplaVerificacaoAmbosValidos() {
        Login login = new LoginDuplaVerificacao(new VerificacaoSenha(), new VerificacaoToken());
        assertTrue(login.entrar("maria"));
    }

    @Test
    @DisplayName("Bridge: dupla verificação com primeiro fator inválido deve falhar")
    void testDuplaVerificacaoPrimeiroFatorFalha() {
        Login login = new LoginDuplaVerificacao(new VerificacaoBiometrica(), new VerificacaoToken());
        assertFalse(login.entrar("ab"));
    }

    @Test
    @DisplayName("Bridge: descrição da dupla verificação deve conter os dois mecanismos")
    void testDuplaVerificacaoDescricao() {
        Login login = new LoginDuplaVerificacao(new VerificacaoSenha(), new VerificacaoBiometrica());
        assertTrue(login.getDescricao().contains("Senha"));
        assertTrue(login.getDescricao().contains("Biometria"));
    }

    // ===================== FACTORY METHOD =====================

    @Test
    @DisplayName("Factory Method: CriadorSenha deve criar VerificacaoSenha")
    void testCriadorSenha() {
        CriadorMecanismo criador = new CriadorSenha();
        MecanismoAuth m = criador.criar();
        assertInstanceOf(VerificacaoSenha.class, m);
        assertEquals("Senha", m.getNome());
    }

    @Test
    @DisplayName("Factory Method: CriadorBiometria deve criar VerificacaoBiometrica")
    void testCriadorBiometria() {
        assertInstanceOf(VerificacaoBiometrica.class, new CriadorBiometria().criar());
    }

    @Test
    @DisplayName("Factory Method: CriadorToken deve criar VerificacaoToken")
    void testCriadorToken() {
        assertInstanceOf(VerificacaoToken.class, new CriadorToken().criar());
    }

    @Test
    @DisplayName("Factory Method: cada chamada deve retornar uma nova instância")
    void testCriadorRetornaNovaInstancia() {
        CriadorMecanismo criador = new CriadorSenha();
        assertNotSame(criador.criar(), criador.criar());
    }

    // ===================== ABSTRACT FACTORY =====================

    @Test
    @DisplayName("Abstract Factory: ProvedorCorporativo cria LoginDuplaVerificacao com Senha + Token")
    void testProvedorCorporativo() {
        var provedor = new ProvedorCorporativo();
        assertEquals("Corporativo", provedor.getNome());
        assertInstanceOf(VerificacaoSenha.class,        provedor.criarMecanismoPrimario());
        assertInstanceOf(VerificacaoToken.class,         provedor.criarMecanismoSecundario());
        assertInstanceOf(LoginDuplaVerificacao.class,    provedor.criarLogin());
    }

    @Test
    @DisplayName("Abstract Factory: ProvedorSocial cria LoginDireto com Token")
    void testProvedorSocial() {
        var provedor = new ProvedorSocial();
        assertEquals("Social", provedor.getNome());
        assertInstanceOf(VerificacaoToken.class, provedor.criarMecanismoPrimario());
        assertInstanceOf(LoginDireto.class,      provedor.criarLogin());
    }

    @Test
    @DisplayName("Abstract Factory: ProvedorAdmin cria LoginDuplaVerificacao com Biometria + Token")
    void testProvedorAdmin() {
        var provedor = new ProvedorAdmin();
        assertEquals("Admin", provedor.getNome());
        assertInstanceOf(VerificacaoBiometrica.class,  provedor.criarMecanismoPrimario());
        assertInstanceOf(VerificacaoToken.class,        provedor.criarMecanismoSecundario());
        assertInstanceOf(LoginDuplaVerificacao.class,   provedor.criarLogin());
    }

    @Test
    @DisplayName("Abstract Factory: provedores distintos criam tipos de login diferentes")
    void testProvedoresCriamFamiliasDiferentes() {
        Login corp   = new ProvedorCorporativo().criarLogin();
        Login social = new ProvedorSocial().criarLogin();
        assertNotEquals(corp.getClass(), social.getClass());
    }

    // ===================== SINGLETON =====================

    @Test
    @DisplayName("Singleton: getInstance sempre retorna a mesma instância")
    void testSingletonMesmaInstancia() {
        assertSame(GerenciadorSessao.getInstance(), GerenciadorSessao.getInstance());
    }

    @Test
    @DisplayName("Singleton: registrar e verificar sessão")
    void testSingletonRegistrarSessao() {
        GerenciadorSessao sessao = GerenciadorSessao.getInstance();
        sessao.registrarSessao("teste_singleton");
        assertTrue(sessao.temSessaoAtiva("teste_singleton"));
        sessao.encerrarSessao("teste_singleton");
    }

    @Test
    @DisplayName("Singleton: encerrar sessão remove o usuário")
    void testSingletonEncerrarSessao() {
        GerenciadorSessao sessao = GerenciadorSessao.getInstance();
        sessao.registrarSessao("usuario_encerrar");
        sessao.encerrarSessao("usuario_encerrar");
        assertFalse(sessao.temSessaoAtiva("usuario_encerrar"));
    }

    @Test
    @DisplayName("Singleton: usuário sem sessão não tem sessão ativa")
    void testSingletonSemSessao() {
        assertFalse(GerenciadorSessao.getInstance().temSessaoAtiva("nao_existe_xyz"));
    }

    // ===================== INTEGRAÇÃO =====================

    @Test
    @DisplayName("Integração: ProvedorCorporativo autentica e registra sessão")
    void testIntegracaoCorporativo() {
        ServicoAutenticacao servico = new ServicoAutenticacao(new ProvedorCorporativo());
        assertTrue(servico.autenticar("carlos"));
        assertTrue(servico.temSessaoAtiva("carlos"));
        servico.encerrarSessao("carlos");
    }

    @Test
    @DisplayName("Integração: ProvedorSocial autentica usuário válido")
    void testIntegracaoSocial() {
        ServicoAutenticacao servico = new ServicoAutenticacao(new ProvedorSocial());
        assertTrue(servico.autenticar("lucas"));
        assertTrue(servico.temSessaoAtiva("lucas"));
        servico.encerrarSessao("lucas");
    }

    @Test
    @DisplayName("Integração: ProvedorAdmin autentica usuário com nome >= 3 chars")
    void testIntegracaoAdmin() {
        ServicoAutenticacao servico = new ServicoAutenticacao(new ProvedorAdmin());
        assertTrue(servico.autenticar("ana"));
        assertTrue(servico.temSessaoAtiva("ana"));
        servico.encerrarSessao("ana");
    }

    @Test
    @DisplayName("Integração: ProvedorAdmin falha com usuário de 2 chars")
    void testIntegracaoAdminFalha() {
        ServicoAutenticacao servico = new ServicoAutenticacao(new ProvedorAdmin());
        assertFalse(servico.autenticar("ab"));
        assertFalse(servico.temSessaoAtiva("ab"));
    }

    @Test
    @DisplayName("Integração: GerenciadorSessao compartilhado entre dois serviços distintos")
    void testSingletonCompartilhadoEntreServicos() {
        ServicoAutenticacao s1 = new ServicoAutenticacao(new ProvedorCorporativo());
        ServicoAutenticacao s2 = new ServicoAutenticacao(new ProvedorSocial());
        s1.autenticar("usuario_shared");
        assertTrue(s2.temSessaoAtiva("usuario_shared"));
        s1.encerrarSessao("usuario_shared");
    }

    @Test
    @DisplayName("Integração: falha na autenticação não registra sessão")
    void testFalhaNaoRegistraSessao() {
        ServicoAutenticacao servico = new ServicoAutenticacao(new ProvedorCorporativo());
        assertFalse(servico.autenticar(null));
        assertFalse(servico.temSessaoAtiva(null));
    }

    @Test
    @DisplayName("Integração: CriadorToken cria mecanismo usado no LoginDireto via Bridge")
    void testIntegracaoCriadorBridge() {
        MecanismoAuth mecanismo = new CriadorToken().criar();
        Login login = new LoginDireto(mecanismo);
        assertTrue(login.entrar("pedro"));
        assertTrue(login.getDescricao().contains("Token OTP"));
    }

    @Test
    @DisplayName("Usuário nulo falha em todos os mecanismos")
    void testNuloFalhaEmTodos() {
        assertAll(
            () -> assertFalse(new VerificacaoSenha().autenticar(null)),
            () -> assertFalse(new VerificacaoBiometrica().autenticar(null)),
            () -> assertFalse(new VerificacaoToken().autenticar(null))
        );
    }
}
