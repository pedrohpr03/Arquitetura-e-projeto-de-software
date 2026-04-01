package autenticacao;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class AutenticacaoTest {

    @Test
    @DisplayName("Login simples com Senha deve autenticar usuário válido")
    void testLoginSimplesComSenha() {
        AutenticacaoAbstrata auth = new LoginSimples(new MecanismoSenha());
        assertTrue(auth.login("joao"));
    }

    @Test
    @DisplayName("Login simples com usuário em branco deve falhar")
    void testLoginSimplesUsuarioBranco() {
        AutenticacaoAbstrata auth = new LoginSimples(new MecanismoSenha());
        assertFalse(auth.login("  "));
    }

    @Test
    @DisplayName("Login simples com usuário nulo deve falhar")
    void testLoginSimplesUsuarioNulo() {
        AutenticacaoAbstrata auth = new LoginSimples(new MecanismoToken());
        assertFalse(auth.login(null));
    }

    @Test
    @DisplayName("Biometria exige usuário com 3 ou mais caracteres")
    void testBiometriaUsuarioCurto() {
        AutenticacaoAbstrata auth = new LoginSimples(new MecanismoBiometria());
        assertFalse(auth.login("ab"));
        assertTrue(auth.login("ana"));
    }

    @Test
    @DisplayName("Bridge: mesmo tipo de login com mecanismos diferentes retorna descrições distintas")
    void testBridgeDescricoesDistintas() {
        AutenticacaoAbstrata comSenha     = new LoginSimples(new MecanismoSenha());
        AutenticacaoAbstrata comBiometria = new LoginSimples(new MecanismoBiometria());

        assertNotEquals(comSenha.getDescricao(), comBiometria.getDescricao());
    }

    @Test
    @DisplayName("getDescricao deve conter o nome do mecanismo")
    void testDescricaoContemMecanismo() {
        AutenticacaoAbstrata auth = new LoginSimples(new MecanismoToken());
        assertTrue(auth.getDescricao().contains("Token OTP"));
    }

    @Test
    @DisplayName("Login multifator com ambos os fatores válidos deve autenticar")
    void testMfaAmbosValidos() {
        AutenticacaoAbstrata mfa = new LoginMultifator(
                new MecanismoSenha(), new MecanismoToken()
        );
        assertTrue(mfa.login("maria"));
    }

    @Test
    @DisplayName("Login multifator com primeiro fator inválido deve falhar sem checar segundo")
    void testMfaPrimeiroFatorFalha() {
        AutenticacaoAbstrata mfa = new LoginMultifator(
                new MecanismoBiometria(), new MecanismoToken()
        );
        assertFalse(mfa.login("ab"));
    }

    @Test
    @DisplayName("Descrição do MFA deve conter os dois mecanismos")
    void testMfaDescricao() {
        AutenticacaoAbstrata mfa = new LoginMultifator(
                new MecanismoSenha(), new MecanismoBiometria()
        );
        String desc = mfa.getDescricao();
        assertTrue(desc.contains("Senha"));
        assertTrue(desc.contains("Biometria"));
    }

    @Test
    @DisplayName("FabricaSenha deve criar instância de MecanismoSenha")
    void testFabricaSenhaCriaCorreto() {
        FabricaAutenticacao fabrica = new FabricaSenha();
        MecanismoAuth m = fabrica.criarMecanismo();
        assertInstanceOf(MecanismoSenha.class, m);
        assertEquals("Senha", m.getNome());
    }

    @Test
    @DisplayName("FabricaBiometria deve criar instância de MecanismoBiometria")
    void testFabricaBiometriaCriaCorreto() {
        FabricaAutenticacao fabrica = new FabricaBiometria();
        assertInstanceOf(MecanismoBiometria.class, fabrica.criarMecanismo());
    }

    @Test
    @DisplayName("FabricaToken deve criar instância de MecanismoToken")
    void testFabricaTokenCriaCorreto() {
        FabricaAutenticacao fabrica = new FabricaToken();
        assertInstanceOf(MecanismoToken.class, fabrica.criarMecanismo());
    }

    @Test
    @DisplayName("Cada chamada à fábrica deve retornar uma nova instância")
    void testFabricaRetornaNovaInstancia() {
        FabricaAutenticacao fabrica = new FabricaSenha();
        assertNotSame(fabrica.criarMecanismo(), fabrica.criarMecanismo());
    }

    @Test
    @DisplayName("Integração: fábrica cria mecanismo e injeta no login via Bridge")
    void testIntegracaoFactoryBridge() {
        FabricaAutenticacao fabrica = new FabricaToken();
        MecanismoAuth mecanismo = fabrica.criarMecanismo();
        AutenticacaoAbstrata auth = new LoginSimples(mecanismo);

        assertTrue(auth.login("pedro"));
        assertTrue(auth.getDescricao().contains("Token OTP"));
    }

    @Test
    @DisplayName("Integração: MFA montado com duas fábricas distintas")
    void testIntegracaoMfaDuasFabricas() {
        MecanismoAuth fator1 = new FabricaSenha().criarMecanismo();
        MecanismoAuth fator2 = new FabricaToken().criarMecanismo();
        AutenticacaoAbstrata mfa = new LoginMultifator(fator1, fator2);

        assertTrue(mfa.login("lucas"));
    }

    @Test
    @DisplayName("Usuário nulo falha em todos os mecanismos")
    void testNuloFalhaEmTodos() {
        assertAll(
                () -> assertFalse(new MecanismoSenha().autenticar(null)),
                () -> assertFalse(new MecanismoBiometria().autenticar(null)),
                () -> assertFalse(new MecanismoToken().autenticar(null))
        );
    }

    @Test
    @DisplayName("Usuário em branco falha em Senha e Token, mas não em Biometria (regra diferente)")
    void testBrancoComportamentoPorMecanismo() {
        assertFalse(new MecanismoSenha().autenticar("  "));
        assertFalse(new MecanismoToken().autenticar("  "));
        assertTrue(new MecanismoBiometria().autenticar("ana"));
    }
}