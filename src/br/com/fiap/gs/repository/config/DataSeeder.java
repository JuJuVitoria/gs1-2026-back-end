package br.com.fiap.gs.repository.config;

import br.com.fiap.gs.enums.*;
import br.com.fiap.gs.model.ai.ChatMessage;
import br.com.fiap.gs.model.user.Farmer;
import br.com.fiap.gs.model.user.PlantationRecord;
import br.com.fiap.gs.model.user.Property;
import br.com.fiap.gs.model.ai.AISuggestion;
import br.com.fiap.gs.model.ai.ChatSession;
import br.com.fiap.gs.model.climate.Agroclimatic;
import br.com.fiap.gs.model.climate.ClimateAlert;
import br.com.fiap.gs.repository.impl.ai.ChatMessageRepository;
import br.com.fiap.gs.repository.impl.user.FarmerRepository;
import br.com.fiap.gs.repository.impl.user.ManagementNotebookRepository;
import br.com.fiap.gs.repository.impl.user.PropertyRepository;
import br.com.fiap.gs.repository.impl.ai.AISuggestionRepository;
import br.com.fiap.gs.repository.impl.ai.ChatSessionRepository;
import br.com.fiap.gs.repository.impl.climate.AgroclimaticRepository;
import br.com.fiap.gs.repository.impl.climate.ClimateAlertRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DataSeeder — popula o banco com dados mockados realistas para demonstração da plataforma.
 *
 * Cenário geral:
 *  - Farmer 1 → Madalena Silva (SC): 2 propriedades, culturas de soja e milho,
 *               alertas de geada/granizo típicos do sul do Brasil.
 *  - Farmer 2 → João Costa (SP/MG): 1 propriedade, cultura de cana-de-açúcar,
 *               alerta de estiagem típico do centro-oeste paulista.
 *  - Farmer 3 → Tereza Ramos (RS): 1 propriedade, cultura de trigo de inverno,
 *               sem alertas ativos — cenário de clima favorável para contraste.
 */

public class DataSeeder {

    private final FarmerRepository farmerRepository;
    private final PropertyRepository propertyRepository;
    private final AgroclimaticRepository agroclimaticRepository;
    private final AISuggestionRepository aiSuggestionRepository;
    private final ManagementNotebookRepository managementNotebookRepository;
    private final ClimateAlertRepository climateAlertRepository;
    private final ChatSessionRepository chatSessionRepository;
    private final ChatMessageRepository chatMessageRepository;

    // =========================================================================
    // FARMER 1 — Madalena Silva (Santa Catarina)
    // =========================================================================
    public static final UUID FARMER_1 = UUID.fromString("00000000-0000-0000-0000-000000000001");

    // Chat sessions
    public static final UUID F1_CHATSESSION1   = UUID.fromString("00000000-0000-0000-0000-000001000001");
    public static final UUID F1_CS1_MESSAGE1   = UUID.fromString("00000000-0000-0000-0000-000011000001");
    public static final UUID F1_CS1_MESSAGE2   = UUID.fromString("00000000-0000-0000-0000-000011000002");
    public static final UUID F1_CS1_MESSAGE3   = UUID.fromString("00000000-0000-0000-0000-000011000003");

    public static final UUID F1_CHATSESSION2   = UUID.fromString("00000000-0000-0000-0000-000002000001");
    public static final UUID F1_CS2_MESSAGE1   = UUID.fromString("00000000-0000-0000-0000-000012000001");
    public static final UUID F1_CS2_MESSAGE2   = UUID.fromString("00000000-0000-0000-0000-000012000002");

    // Propriedade 1 — "Recanto da Luz" (Curitibanos, SC) — soja
    public static final UUID FARMER_1_PROPERTY_1        = UUID.fromString("00000000-0000-0000-0000-000000000011");
    public static final UUID F1_P1_AGROCLIMATIC1        = UUID.fromString("00000000-0000-0000-0000-000000000111");
    public static final UUID F1_P1_AISUGGESTION1        = UUID.fromString("00000000-0000-0000-0000-000000001011");
    public static final UUID F1_P1_AISUGGESTION2        = UUID.fromString("00000000-0000-0000-0000-000000001012");
    public static final UUID F1_P1_PLANTATIONRECORD1    = UUID.fromString("00000000-0000-0000-0000-000000010011");
    public static final UUID F1_P1_PLANTATIONRECORD2    = UUID.fromString("00000000-0000-0000-0000-000000010012");
    public static final UUID F1_P1_PLANTATIONRECORD3    = UUID.fromString("00000000-0000-0000-0000-000000010013");
    public static final UUID F1_P1_ALERT1               = UUID.fromString("00000000-0000-0000-0000-000000100011");
    public static final UUID F1_P1_ALERT2               = UUID.fromString("00000000-0000-0000-0000-000000100012");

    // Propriedade 2 — "Chácara das Flores" (Campos Novos, SC) — milho
    public static final UUID FARMER_1_PROPERTY_2        = UUID.fromString("00000000-0000-0000-0000-000000000021");
    public static final UUID F1_P2_AGROCLIMATIC1        = UUID.fromString("00000000-0000-0000-0000-000000000121");
    public static final UUID F1_P2_AISUGGESTION1        = UUID.fromString("00000000-0000-0000-0000-000000001021");
    public static final UUID F1_P2_PLANTATIONRECORD1    = UUID.fromString("00000000-0000-0000-0000-000000010021");
    public static final UUID F1_P2_PLANTATIONRECORD2    = UUID.fromString("00000000-0000-0000-0000-000000010022");
    public static final UUID F1_P2_ALERT1               = UUID.fromString("00000000-0000-0000-0000-000000100021");

    // =========================================================================
    // FARMER 2 — João Costa (São Paulo / Triângulo Mineiro)
    // =========================================================================
    public static final UUID FARMER_2 = UUID.fromString("00000000-0000-0000-0000-000000000002");

    // Chat sessions
    public static final UUID F2_CHATSESSION1   = UUID.fromString("00000000-0000-0000-0000-000001000002");
    public static final UUID F2_CS1_MESSAGE1   = UUID.fromString("00000000-0000-0000-0000-000011000004");
    public static final UUID F2_CS1_MESSAGE2   = UUID.fromString("00000000-0000-0000-0000-000011000005");

    // Propriedade 1 — "Pedacinho do Céu" (Barretos, SP) — cana-de-açúcar
    public static final UUID FARMER_2_PROPERTY_1        = UUID.fromString("00000000-0000-0000-0000-000000000012");
    public static final UUID F2_P1_AGROCLIMATIC1        = UUID.fromString("00000000-0000-0000-0000-000000000112");
    public static final UUID F2_P1_AISUGGESTION1        = UUID.fromString("00000000-0000-0000-0000-000000001012");
    public static final UUID F2_P1_PLANTATIONRECORD1    = UUID.fromString("00000000-0000-0000-0000-000000010012");
    public static final UUID F2_P1_PLANTATIONRECORD2    = UUID.fromString("00000000-0000-0000-0000-000000010014");
    public static final UUID F2_P1_ALERT1               = UUID.fromString("00000000-0000-0000-0000-000000100012");

    // =========================================================================
    // FARMER 3 — Tereza Ramos (Rio Grande do Sul)
    // =========================================================================
    public static final UUID FARMER_3 = UUID.fromString("00000000-0000-0000-0000-000000000003");

    // Chat sessions
    public static final UUID F3_CHATSESSION1   = UUID.fromString("00000000-0000-0000-0000-000001000003");
    public static final UUID F3_CS1_MESSAGE1   = UUID.fromString("00000000-0000-0000-0000-000011000006");
    public static final UUID F3_CS1_MESSAGE2   = UUID.fromString("00000000-0000-0000-0000-000011000007");

    // Propriedade 1 — "Vale do Trigal" (Cruz Alta, RS) — trigo
    public static final UUID FARMER_3_PROPERTY_1        = UUID.fromString("00000000-0000-0000-0000-000000000013");
    public static final UUID F3_P1_AGROCLIMATIC1        = UUID.fromString("00000000-0000-0000-0000-000000000113");
    public static final UUID F3_P1_AISUGGESTION1        = UUID.fromString("00000000-0000-0000-0000-000000001013");
    public static final UUID F3_P1_PLANTATIONRECORD1    = UUID.fromString("00000000-0000-0000-0000-000000010013");
    public static final UUID F3_P1_PLANTATIONRECORD2    = UUID.fromString("00000000-0000-0000-0000-000000010015");
    // Farmer 3 não possui alertas ativos — cenário favorável

    // =========================================================================

    public DataSeeder(FarmerRepository farmerRepository,
                      PropertyRepository propertyRepository,
                      AgroclimaticRepository agroclimaticRepository,
                      AISuggestionRepository aiSuggestionRepository,
                      ManagementNotebookRepository managementNotebookRepository,
                      ClimateAlertRepository climateAlertRepository,
                      ChatSessionRepository chatSessionRepository,
                      ChatMessageRepository chatMessageRepository) {
        this.farmerRepository = farmerRepository;
        this.propertyRepository = propertyRepository;
        this.agroclimaticRepository = agroclimaticRepository;
        this.aiSuggestionRepository = aiSuggestionRepository;
        this.managementNotebookRepository = managementNotebookRepository;
        this.climateAlertRepository = climateAlertRepository;
        this.chatSessionRepository = chatSessionRepository;
        this.chatMessageRepository = chatMessageRepository;
        seed();
    }

    private void seed() {
        seedFarmer1();
        seedFarmer2();
        seedFarmer3();
    }

    // =========================================================================
    // FARMER 1 — Madalena Silva
    // Região serrana de SC, altitude ~1.000 m. Vulnerável a geadas de maio–agosto.
    // =========================================================================
    private void seedFarmer1() {
        Farmer farmer1 = new Farmer(
                FARMER_1,
                "Madalena Silva",
                "madalena@astrocrop.com.br",
                "senha123"
        );
        farmerRepository.save(farmer1);

        ChatSession f1cs1 = new ChatSession(
                F1_CHATSESSION1, FARMER_1,
                "Risco de geada na janela de plantio",
                LocalDate.now().minusDays(3)
        );
        chatSessionRepository.save(f1cs1);

        chatMessageRepository.save(new ChatMessage(
                F1_CS1_MESSAGE1, F1_CHATSESSION1, SenderType.FARMER,
                "Tenho soja na fase de floração e o satélite mostrou temperatura mínima de 2 °C para essa sexta-feira. " +
                        "Preciso proteger a lavoura ou esperar passar?",
                LocalDateTime.now().minusDays(3).withHour(8).withMinute(14)
        ));
        chatMessageRepository.save(new ChatMessage(
                F1_CS1_MESSAGE2, F1_CHATSESSION1, SenderType.BOT,
                "Madalena, a soja em plena floração é sensível a temperaturas abaixo de 4 °C. " +
                        "Com mínima prevista de 2 °C, o risco de dano floral é moderado a alto. " +
                        "Recomendo irrigação protetiva por aspersão durante a madrugada (22 h–06 h), " +
                        "pois a formação de uma fina camada de gelo sobre as folhas libera calor latente " +
                        "e mantém a temperatura do tecido acima de 0 °C. " +
                        "Verifique se o sistema de irrigação está pronto e se o reservatório está cheio.",
                LocalDateTime.now().minusDays(3).withHour(8).withMinute(21)
        ));
        chatMessageRepository.save(new ChatMessage(
                F1_CS1_MESSAGE3, F1_CHATSESSION1, SenderType.FARMER,
                "Entendido! Vou ligar a aspersão. E depois da geada, o que verifico na lavoura?",
                LocalDateTime.now().minusDays(3).withHour(9).withMinute(5)
        ));

        ChatSession f1cs2 = new ChatSession(
                F1_CHATSESSION2, FARMER_1,
                "Avaliação de danos após granizo",
                LocalDate.now().minusDays(1)
        );
        chatSessionRepository.save(f1cs2);

        chatMessageRepository.save(new ChatMessage(
                F1_CS2_MESSAGE1, F1_CHATSESSION2, SenderType.FARMER,
                "Caiu granizo ontem à tarde, durou uns 20 minutos. Algumas folhas estão rasgadas " +
                        "e vi pedras de até 1 cm. A soja está em R3 (início de granação). O que fazer?",
                LocalDateTime.now().minusDays(1).withHour(7).withMinute(30)
        ));
        chatMessageRepository.save(new ChatMessage(
                F1_CS2_MESSAGE2, F1_CHATSESSION2, SenderType.BOT,
                "Em R3, granizo com pedras de 1 cm pode causar perda de 10–25 % da produção dependendo da densidade. " +
                        "Faça o levantamento: caminhe em ziguezague pela lavoura e conte, em 5 pontos de 1 m², " +
                        "quantas vagens foram danificadas ou perdidas. Se a perda média superar 20 %, " +
                        "acione o seguro rural ainda hoje (prazo: até 72 h do evento). " +
                        "Fotografe os danos com timestamp ativo no celular para compor o laudo.",
                LocalDateTime.now().minusDays(1).withHour(7).withMinute(45)
        ));

        // --- Propriedade 1: Recanto da Luz — Curitibanos, SC — soja verão ---
        Property f1p1 = new Property(
                FARMER_1_PROPERTY_1, FARMER_1,
                "Recanto da Luz",
                -27.283942, -50.579381
        );
        propertyRepository.save(f1p1);

        agroclimaticRepository.save(new Agroclimatic(
                F1_P1_AGROCLIMATIC1, FARMER_1_PROPERTY_1,
                LocalDate.now(), 14, 1,
                0, 88, 18
        ));

        // Sugestão IA: janela de plantio comprometida
        aiSuggestionRepository.save(new AISuggestion(
                F1_P1_AISUGGESTION1, FARMER_1_PROPERTY_1,
                "Alerta crítico: temperatura mínima de 1 °C registrada esta madrugada. " +
                        "A soja em floração pode ter sofrido abortamento floral. " +
                        "Realize vistoria nas próximas 48 h e aplique aminoácidos foliares para " +
                        "reduzir o estresse pós-geada. Evite qualquer aplicação de herbicida esta semana.",
                SuggestionType.ALERT,
                LocalDate.now()
        ));
        aiSuggestionRepository.save(new AISuggestion(
                F1_P1_AISUGGESTION2, FARMER_1_PROPERTY_1,
                "Janela favorável de colheita prevista para daqui a 22–28 dias (umidade do grão estimada: 14–16 %). " +
                        "Programe o contato com a cooperativa para garantir disponibilidade de secador " +
                        "caso a colheita coincida com período chuvoso.",
                SuggestionType.HARVEST,
                LocalDate.now().plusDays(2)
        ));

        // Caderno de campo
        managementNotebookRepository.save(new PlantationRecord(
                F1_P1_PLANTATIONRECORD1, FARMER_1_PROPERTY_1, ActivityType.PLANTIO,
                "Semeadura de soja var. TMG 7062 IPRO a 14 sementes/m². Solo corrigido, " +
                        "inoculação com Bradyrhizobium realizada. Umidade do solo adequada (70 % CC).",
                LocalDateTime.now().minusDays(75).withHour(6).withMinute(0)
        ));
        managementNotebookRepository.save(new PlantationRecord(
                F1_P1_PLANTATIONRECORD2, FARMER_1_PROPERTY_1, ActivityType.MANEJO,
                "Aplicação de fungicida (Aproach Prima) em R1 para controle preventivo de ferrugem asiática. " +
                        "Condição: 26 °C, 65 % UR, vento 8 km/h. Volume de calda: 100 L/ha.",
                LocalDateTime.now().minusDays(10).withHour(7).withMinute(30)
        ));
        managementNotebookRepository.save(new PlantationRecord(
                F1_P1_PLANTATIONRECORD3, FARMER_1_PROPERTY_1, ActivityType.MANEJO,
                "Vistoria pós-geada: observadas manchas cloróticas em ~12 % das plantas. " +
                        "Aplicação foliar de aminoácidos (300 mL/ha). Monitoramento diário estabelecido.",
                LocalDateTime.now().minusHours(6)
        ));

        // Alertas ativos
        climateAlertRepository.save(new ClimateAlert(
                F1_P1_ALERT1, FARMER_1_PROPERTY_1,
                "Geada registrada — temperatura mínima de 1 °C às 04h37. " +
                        "Risco de abortamento floral e danos em R1-R3.",
                RiskLevel.ALTO, AlertType.GEADA,
                LocalDate.now(),
                LifeCycle.ATIVO
        ));
        climateAlertRepository.save(new ClimateAlert(
                F1_P1_ALERT2, FARMER_1_PROPERTY_1,
                "Ventos fortes previstos para amanhã (rajadas de até 65 km/h). " +
                        "Risco de acamamento em lavoura de soja em estádio R3–R4.",
                RiskLevel.MEDIO, AlertType.VENTO_FORTE,
                LocalDate.now().plusDays(1),
                LifeCycle.ATIVO
        ));

        // --- Propriedade 2: Chácara das Flores — Campos Novos, SC — milho safrinha ---
        Property f1p2 = new Property(
                FARMER_1_PROPERTY_2, FARMER_1,
                "Chácara das Flores",
                -27.399021, -51.225613
        );
        propertyRepository.save(f1p2);

        agroclimaticRepository.save(new Agroclimatic(
                F1_P2_AGROCLIMATIC1, FARMER_1_PROPERTY_2,
                LocalDate.now(), 28, 12, 2, 58, 10
        ));

        aiSuggestionRepository.save(new AISuggestion(
                F1_P2_AISUGGESTION1, FARMER_1_PROPERTY_2,
                "Deficiência hídrica detectada: apenas 2 mm de chuva nos últimos 7 dias " +
                        "(necessidade do milho em V8: ~6 mm/dia). " +
                        "Recomenda-se irrigação suplementar de 20–25 mm nos próximos 3 dias. " +
                        "Janela favorável: amanhã das 06h às 09h (vento < 12 km/h).",
                SuggestionType.IRRIGATION,
                LocalDate.now()
        ));

        managementNotebookRepository.save(new PlantationRecord(
                F1_P1_PLANTATIONRECORD1, FARMER_1_PROPERTY_2, ActivityType.PLANTIO,
                "Semeadura de milho híbrido P3844H a 62.000 sementes/ha. " +
                        "Adubação de base: 300 kg/ha do formulado 8-20-20.",
                LocalDateTime.now().minusDays(45).withHour(6).withMinute(30)
        ));
        managementNotebookRepository.save(new PlantationRecord(
                F1_P2_PLANTATIONRECORD2, FARMER_1_PROPERTY_2, ActivityType.MANEJO,
                "Adubação nitrogenada de cobertura (ureia): 120 kg N/ha em V6. " +
                        "Solo úmido, aplicação incorporada antes de chuva prevista.",
                LocalDateTime.now().minusDays(15).withHour(7).withMinute(0)
        ));

        climateAlertRepository.save(new ClimateAlert(
                F1_P2_ALERT1, FARMER_1_PROPERTY_2,
                "Granizo de intensidade moderada confirmado pelo satélite Copernicus às 15h22. " +
                        "Pedras de 0,5–1 cm registradas em área de 3,2 km². Avalie danos na lavoura de milho em V8.",
                RiskLevel.ALTO, AlertType.GRANIZO,
                LocalDate.now().minusDays(1),
                LifeCycle.ATIVO
        ));
    }

    // =========================================================================
    // FARMER 2 — João Costa
    // Região de Barretos, SP. Clima tropical seco, risco de estiagem no inverno.
    // =========================================================================

    private void seedFarmer2() {
        Farmer farmer2 = new Farmer(
                FARMER_2,
                "João Costa",
                "joao@astrocrop.com.br",
                "senha456"
        );
        farmerRepository.save(farmer2);

        ChatSession f2cs1 = new ChatSession(
                F2_CHATSESSION1, FARMER_2,
                "Planejamento de irrigação na seca",
                LocalDate.now().minusDays(2)
        );
        chatSessionRepository.save(f2cs1);

        chatMessageRepository.save(new ChatMessage(
                F2_CS1_MESSAGE1, F2_CHATSESSION1, SenderType.FARMER,
                "Já faz 22 dias sem chuva aqui em Barretos. Minha cana está em fase de " +
                        "crescimento rápido e sinto que está estressando. O satélite SMAP indica déficit hídrico. " +
                        "Como calcular quanto irrigar?",
                LocalDateTime.now().minusDays(2).withHour(10).withMinute(5)
        ));
        chatMessageRepository.save(new ChatMessage(
                F2_CS1_MESSAGE2, F2_CHATSESSION1, SenderType.BOT,
                "João, para cana em grand growth com ETo de ~5 mm/dia (típico de Barretos no inverno) " +
                        "e Kc ≈ 1,25, a ETc estimada é de 6,25 mm/dia. Com 22 dias sem chuva, o déficit " +
                        "acumulado chega a ~137 mm. Recomendo repor 40–50 mm em 2 turnos de rega (gotejamento " +
                        "ou pivô), espaçados 3–4 dias, para não causar encharcamento. " +
                        "Use o índice NDWI do Copernicus disponível no painel para monitorar a recuperação foliar.",
                LocalDateTime.now().minusDays(2).withHour(10).withMinute(22)
        ));

        Property f2p1 = new Property(
                FARMER_2_PROPERTY_1, FARMER_2,
                "Pedacinho do Céu",
                -20.557891, -48.563004
        );
        propertyRepository.save(f2p1);

        agroclimaticRepository.save(new Agroclimatic(
                F2_P1_AGROCLIMATIC1, FARMER_2_PROPERTY_1,
                LocalDate.now(), 34, 18,
                0, 31, 22
        ));

        aiSuggestionRepository.save(new AISuggestion(
                F2_P1_AISUGGESTION1, FARMER_2_PROPERTY_1,
                "Déficit hídrico severo: 22 dias sem precipitação significativa. " +
                        "Satélite SMAP indica umidade do solo abaixo de 30 % da capacidade de campo. " +
                        "Iniciar irrigação de emergência: 50 mm em 2 turnos (hoje e em 4 dias). " +
                        "Evitar aplicação de defensivos até normalização da umidade foliar.",
                SuggestionType.IRRIGATION,
                LocalDate.now()
        ));

        managementNotebookRepository.save(new PlantationRecord(
                F2_P1_PLANTATIONRECORD1, FARMER_2_PROPERTY_1, ActivityType.PLANTIO,
                "Plantio de cana-de-açúcar var. RB867515 (mudas pré-brotadas). " +
                        "Espaçamento: 1,5 m entre linhas. Sulcação com adubação de fundo: " +
                        "400 kg/ha do formulado 5-25-25.",
                LocalDateTime.now().minusDays(120).withHour(5).withMinute(45)
        ));
        managementNotebookRepository.save(new PlantationRecord(
                F2_P1_PLANTATIONRECORD2, FARMER_2_PROPERTY_1, ActivityType.MANEJO,
                "Irrigação de salvamento por pivô central: 30 mm aplicados em 6 h. " +
                        "Pressão de serviço: 2,8 bar. Motor em funcionamento normal.",
                LocalDateTime.now().minusHours(12)
        ));

        climateAlertRepository.save(new ClimateAlert(
                F2_P1_ALERT1, FARMER_2_PROPERTY_1,
                "Estiagem prolongada — 22 dias sem chuva. " +
                        "Índice de aridez crítico detectado pelo ZARC/Embrapa para a região de Barretos. " +
                        "Risco de queda de produtividade acima de 20 % se não houver irrigação suplementar.",
                RiskLevel.ALTO, AlertType.ESTIAGEM,
                LocalDate.now(),
                LifeCycle.ATIVO
        ));
    }

    // =========================================================================
    // FARMER 3 — Tereza Ramos
    // Cruz Alta, RS. Clima temperado, trigo de inverno — sem alertas (cenário verde).
    // =========================================================================

    private void seedFarmer3() {
        Farmer farmer3 = new Farmer(
                FARMER_3,
                "Tereza Ramos",
                "tereza@astrocrop.com.br",
                "senha789"
        );
        farmerRepository.save(farmer3);

        ChatSession f3cs1 = new ChatSession(
                F3_CHATSESSION1, FARMER_3,
                "Previsão de colheita e adubação de cobertura",
                LocalDate.now().minusDays(5)
        );
        chatSessionRepository.save(f3cs1);

        chatMessageRepository.save(new ChatMessage(
                F3_CS1_MESSAGE1, F3_CHATSESSION1, SenderType.FARMER,
                "Meu trigo está em espigamento (escala de Zadoks 59). " +
                        "O clima está favorável, temperatura entre 18 e 24 °C e boa umidade. " +
                        "Devo fazer adubação foliar de nitrogênio agora ou esperar?",
                LocalDateTime.now().minusDays(5).withHour(9).withMinute(0)
        ));
        chatMessageRepository.save(new ChatMessage(
                F3_CS1_MESSAGE2, F3_CHATSESSION1, SenderType.BOT,
                "Tereza, em Zadoks 59 (espigas totalmente emergidas) a adubação nitrogenada foliar " +
                        "ainda traz benefício para proteína do grão, especialmente se o objetivo é trigo pão " +
                        "(proteína > 12 %). Aplique ureia foliar a 3 % (30 kg/100 L) no início da manhã ou " +
                        "fim de tarde, com temperatura abaixo de 28 °C para evitar queima foliar. " +
                        "Com o clima favorável que você descreveu, a janela dos próximos 3 dias é excelente.",
                LocalDateTime.now().minusDays(5).withHour(9).withMinute(18)
        ));

        Property f3p1 = new Property(
                FARMER_3_PROPERTY_1, FARMER_3,
                "Vale do Trigal",
                -28.638457, -53.605812
        );
        propertyRepository.save(f3p1);

        // Telemetria: clima ideal para trigo
        agroclimaticRepository.save(new Agroclimatic(
                F3_P1_AGROCLIMATIC1, FARMER_3_PROPERTY_1,
                LocalDate.now(),22, 11,
                8, 72, 7
        ));

        aiSuggestionRepository.save(new AISuggestion(
                F3_P1_AISUGGESTION1, FARMER_3_PROPERTY_1,
                "Condições climáticas excelentes para a fase de enchimento de grãos. " +
                        "Previsão de colheita em 18–22 dias (umidade estimada do grão na colheita: 15–18 %). " +
                        "Recomenda-se monitorar oídio e septoriose nas próximas 10 dias. " +
                        "Janela ideal para fungicida de espiga: entre amanhã e 3 dias após.",
                SuggestionType.HARVEST,
                LocalDate.now()
        ));

        managementNotebookRepository.save(new PlantationRecord(
                F3_P1_PLANTATIONRECORD1, FARMER_3_PROPERTY_1, ActivityType.PLANTIO,
                "Semeadura de trigo var. TBIO Sonic a 350 sementes/m². " +
                        "Tratamento de sementes com fungicida + inseticida. Adubação: 250 kg/ha de 5-20-20.",
                LocalDateTime.now().minusDays(90).withHour(7).withMinute(0)
        ));
        managementNotebookRepository.save(new PlantationRecord(
                F3_P1_PLANTATIONRECORD2, FARMER_3_PROPERTY_1, ActivityType.MANEJO,
                "Aplicação de fungicida foliar (Elatus) em Zadoks 37 para controle de septoriose. " +
                        "Temperatura: 20 °C, UR: 68 %, vento: 6 km/h. Volume de calda: 150 L/ha.",
                LocalDateTime.now().minusDays(20).withHour(8).withMinute(0)
        ));
    }
}