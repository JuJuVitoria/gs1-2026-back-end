# 🌾 AstroCrop — Back-end Java (Console Application)

> **Global Solution 2026/1 · 2º Ano Engenharia de Software · FIAP**
> Solução de inteligência agroclimática que traduz dados orbitais de satélites em alertas e planos de manejo práticos para o pequeno e médio produtor rural.

---

## 📋 Sumário

- [Sobre o Projeto](#sobre-o-projeto)
- [Funcionalidades](#funcionalidades)
- [Arquitetura e Estrutura de Pacotes](#arquitetura-e-estrutura-de-pacotes)
- [Modelos de Dados](#modelos-de-dados)
- [Enums](#enums)
- [Camada de Repositório](#camada-de-repositório)
- [Camada de Serviço](#camada-de-serviço)
- [Menu / View (Console)](#menu--view-console)
- [Como Executar](#como-executar)
- [Requisitos Funcionais Implementados](#requisitos-funcionais-implementados)
- [Requisitos Não Funcionais](#requisitos-não-funcionais)
- [UML do Projeto](#uml-do-projeto)
- [Conexão com ODS da ONU](#conexão-com-ods-da-onu)
- [Equipe](#equipe)

---

## Sobre o Projeto

O **AstroCrop** é o módulo back-end (Java/console) de uma plataforma agroclimática que democratiza o acesso a dados espaciais para produtores rurais. A aplicação simula a ingestão de telemetria climática proveniente de satélites (constelações **Copernicus/ESA** e **SMAP/NASA**) e do zoneamento de risco climático (**ZARC/Embrapa**), gerando alertas de risco e recomendações de manejo acessíveis diretamente no campo.

O sistema é executado integralmente via **console**, sem integração com banco de dados nesta fase — todos os dados são mantidos em memória por meio de coleções `ArrayList`, conforme as diretrizes da coordenação FIAP para o 2º ano.

---

## Funcionalidades

| # | Funcionalidade | Prioridade |
|---|---|---|
| RF01 | Cadastro e autenticação simulada de produtor | ALTA |
| RF02 | Gerenciamento de propriedades rurais (CRUD) | MÉDIA |
| RF03 | Consulta de dados climáticos simulados por coordenadas | ALTA |
| RF04 | Diário/caderno de campo digital (Plantio, Manejo, Colheita) | BAIXA |
| RF05 | Recomendador de plantio e alertas de risco (IA simulada) | MÉDIA |

---

## Arquitetura e Estrutura de Pacotes

O projeto segue a arquitetura em camadas (Layered Architecture), com separação clara entre entidades de domínio, lógica de negócio e interface com o usuário.

```
src/
└── br/com/fiap/gs/
    ├── config/
    │   └── ApplicationContext.java        # Inicialização e wiring dos objetos
    ├── enums/
    │   ├── ActivityType.java
    │   ├── AlertType.java
    │   ├── CropType.java
    │   ├── LifeCycle.java
    │   ├── RiskLevel.java
    │   ├── SenderType.java
    │   └── SuggestionType.java
    ├── model/
    │   ├── ai/
    │   │   ├── AISuggestion.java
    │   │   ├── ChatMessage.java
    │   │   └── ChatSession.java
    │   ├── climate/
    │   │   ├── Agroclimatic.java
    │   │   └── ClimateAlert.java
    │   └── user/
    │       ├── Farmer.java
    │       ├── PlantationRecord.java
    │       └── Property.java
    ├── repository/
    │   ├── config/
    │   │   └── DataSeeder.java            # Carga inicial de dados simulados
    │   ├── impl/
    │   │   ├── ai/
    │   │   │   ├── AISuggestionRepository.java
    │   │   │   ├── ChatMessageRepository.java
    │   │   │   └── ChatSessionRepository.java
    │   │   ├── climate/
    │   │   │   ├── AgroclimaticRepository.java
    │   │   │   └── ClimateAlertRepository.java
    │   │   └── user/
    │   │       ├── FarmerRepository.java
    │   │       ├── ManagementNotebookRepository.java
    │   │       └── PropertyRepository.java
    │   ├── InMemoryRepository.java        # Repositório genérico em memória
    │   └── Repository.java               # Interface base de repositório
    ├── service/
    │   ├── impl/
    │   │   ├── AgroIntelligenceServiceImpl.java
    │   │   ├── AuthServiceImpl.java
    │   │   ├── ChatServiceImpl.java
    │   │   ├── ClimateServiceImpl.java
    │   │   ├── FarmerServiceImpl.java
    │   │   ├── ManagementNotebookServiceImpl.java
    │   │   └── PropertyServiceImpl.java
    │   └── interfaces/
    │       ├── AgroIntelligenceService.java
    │       ├── AuthService.java
    │       ├── ChatService.java
    │       ├── ClimateService.java
    │       ├── FarmerService.java
    │       ├── ManagementNotebookService.java
    │       └── PropertyService.java
    ├── view/
    │   ├── AgroclimaticMenu.java
    │   ├── ChatMenu.java
    │   ├── LoginMenu.java
    │   ├── MainMenu.java
    │   └── ManagementMenu.java
    └── Main.java        # Executével para rodar o projeto
```

---

## Modelos de Dados

### `model/user/`

**`Farmer`** — Representa o produtor rural cadastrado no sistema.

**`Property`** — Propriedade rural vinculada ao produtor, contendo nome da área, cultura principal, coordenadas geográficas, tamanho em hectares e região/estado.

**`PlantationRecord`** — Registro de uma atividade no caderno de campo, categorizado por tipo (Plantio, Manejo ou Colheita), com data e observações.

### `model/climate/`

**`Agroclimatic`** — Dados de telemetria climática simulada (precipitação, umidade, velocidade do vento) associados a coordenadas geográficas.

**`ClimateAlert`** — Alerta climático gerado pelo sistema (geada, estiagem, vento forte, etc.) com nível de risco e cultura afetada.

### `model/ai/`

**`AISuggestion`** — Recomendação de plantio ou manejo gerada pelo módulo de inteligência agroclimática.

**`ChatSession`** / **`ChatMessage`** — Representam sessões de consulta interativa no console, onde o produtor pode tirar dúvidas sobre manejo.

---

## Enums

| Enum | Descrição |
|---|---|
| `ActivityType` | Tipos de atividade no caderno de campo: `PLANTIO`, `MANEJO`, `COLHEITA` |
| `AlertType` | Tipos de alerta climático: geada, estiagem, vento forte, etc. |
| `CropType` | Culturas suportadas pelo sistema (soja, milho, trigo, etc.) |
| `LifeCycle` | Fase fenológica da cultura (germinação, floração, maturação) |
| `RiskLevel` | Nível de risco do alerta: `BAIXO`, `MÉDIO`, `ALTO`, `CRÍTICO` |
| `SenderType` | Identifica o remetente de uma mensagem de chat: `USER` ou `SYSTEM` |
| `SuggestionType` | Classifica o tipo de sugestão da IA: plantio, adubação, irrigação, etc. |

---

## Camada de Repositório

Todos os repositórios estendem `InMemoryRepository<T>`, que implementa a interface genérica `Repository<T>`, garantindo as operações básicas de CRUD em memória sem dependência de banco de dados.

```
Repository<T>  (interface)
    └── InMemoryRepository<T>  (implementação genérica com ArrayList)
            ├── FarmerRepository
            ├── PropertyRepository
            ├── ManagementNotebookRepository
            ├── AgroclimaticRepository
            ├── ClimateAlertRepository
            ├── AISuggestionRepository
            ├── ChatSessionRepository
            └── ChatMessageRepository
```

O **`DataSeeder`** popula os repositórios com dados iniciais simulados na inicialização da aplicação, permitindo demonstração completa do fluxo sem necessidade de entrada manual.

---

## Camada de Serviço

Cada serviço possui uma **interface** (em `service/interfaces/`) e uma **implementação** (em `service/impl/`), respeitando o princípio da inversão de dependência.

| Serviço | Responsabilidade |
|---|---|
| `AuthService` | Autenticação simulada do produtor via console |
| `FarmerService` | Cadastro, listagem e busca de produtores |
| `PropertyService` | CRUD de propriedades rurais |
| `ClimateService` | Simulação de telemetria climática e geração de alertas |
| `AgroIntelligenceService` | Motor de recomendação de plantio (IA simulada) |
| `ManagementNotebookService` | Gestão do caderno de campo digital |
| `ChatService` | Sessões de consulta interativa no console |

---

## Menu / View (Console)

A camada `view` contém os menus interativos exibidos no terminal, organizados por contexto:

- **`MainMenu`** — Ponto de entrada principal; direciona para login ou cadastro.
- **`LoginMenu`** — Autenticação do produtor (simulada).
- **`AgroclimaticMenu`** — Consulta de dados climáticos e alertas de risco por propriedade.
- **`ManagementMenu`** — Registro e listagem de atividades no caderno de campo; recomendações da IA.
- **`ChatMenu`** — Interface de chat textual para consultas sobre manejo e cultivo.

---

## Como Executar

**Pré-requisitos:** Java 26+ e Maven (ou execute diretamente pela IDE).

```bash
# Clone o repositório
git clone https://github.com/JuJuVitoria/gs1-2026-back-end.git
cd gs1-2026-back-end

# Compile e execute
mvn compile exec:java -Dexec.mainClass="br.com.fiap.gs.Main"
```

Ou abra o projeto na **IntelliJ IDEA** e execute a classe `Main.java`.

---

## Requisitos Funcionais Implementados

- **RF01** — Cadastro e login simulado do produtor via menu console.
- **RF02** — CRUD completo de propriedades rurais (nome, cultura, coordenadas, hectares, região).
- **RF03** — Simulação de consulta de telemetria climática (chuva, umidade, vento) a partir das coordenadas da propriedade.
- **RF04** — Registro e listagem de atividades no caderno de campo com categorização por tipo e ciclo de vida.
- **RF05** — Motor de recomendação que cruza região e condições climáticas para sugerir culturas de menor risco e disparar alertas para culturas em fase de colheita.

---

## Requisitos Não Funcionais

- **RNF02** — Back-end 100% em **Java**, com uso de encapsulamento, construtores, interfaces e coleções (`ArrayList`), conforme as diretrizes FIAP para o 2º ano.
- **RNF03** — Dados gerenciados inteiramente **em memória** (sem integração com banco nesta fase). Validações de entrada garantem estabilidade da simulação de CRUD.
- Separação clara entre camadas: `model` → `repository` → `service` → `view`.
- Ausência de `NullPointerException` em fluxo nominal garantida por validações nos serviços.

> **Fora do escopo desta fase:** integração entre Back-End, Front-End e Banco de Dados. O script SQL (.sql) e o protótipo web (HTML/CSS/JS) são entregues como artefatos independentes.

---

## Conexão com ODS da ONU

| ODS | Contribuição do AstroCrop |
|---|---|
| **ODS 2** — Fome Zero e Agricultura Sustentável | Janelas assertivas de plantio e alertas de risco reduzem a perda de insumos em até 30% na fase inicial do cultivo. |
| **ODS 13** — Ação Contra a Mudança Global do Clima | Adaptação dinâmica do calendário de cultivo frente a eventos climáticos extremos (geadas, estiagens, ventos fortes). |

---

## UML do Projeto

O diagrama UML abaixo representa a modelagem arquitetural elaborada durante a fase de análise e planejamento da solução. Seu objetivo foi definir as principais entidades do domínio, os relacionamentos entre os componentes e a organização da aplicação em camadas antes do início da implementação.

> Importante: Durante o desenvolvimento, algumas decisões de projeto foram refinadas para melhorar a organização do código, reduzir acoplamento e aumentar a reutilização de componentes. Dessa forma, a implementação final pode apresentar pequenas diferenças em relação ao UML originalmente proposto. O diagrama deve ser interpretado como uma representação da arquitetura planejada e da visão conceitual do sistema.

### Visão Geral da Arquitetura

A solução foi projetada seguindo os princípios da Layered Architecture (Arquitetura em Camadas), separando responsabilidades entre:

- Model: representa as entidades de negócio e estruturas de dados do domínio agroclimático.
- Repository: responsável pelo acesso e gerenciamento dos dados em memória.
- Service: concentra as regras de negócio, validações e processamento das informações.
- View: implementa a interface de interação via console utilizada pelo produtor rural.

Essa separação facilita a manutenção, evolução e testabilidade da aplicação.

### Diagrama UML Completo

O diagrama UML abaixo representa a modelagem inicial proposta para a solução durante a fase de análise e design do sistema.

<img width="5660" height="3640" alt="UML GS1-2026" src="https://github.com/user-attachments/assets/241edefb-bfae-4e42-9302-db08732802aa" />

#### Camada de Modelos (Model)
A camada de modelos contém as entidades centrais da aplicação, representando produtores rurais, propriedades, registros de manejo, previsões climáticas, alertas e interações com o módulo de inteligência agroclimática.

<img width="4096" height="2249" alt="image" src="https://github.com/user-attachments/assets/26c639d8-cfd3-4255-82b8-27815e533a43" />

#### Camada de Repositório (Repository)
A camada de repositório foi modelada para encapsular o armazenamento dos dados da aplicação e abstrair as operações de acesso às coleções em memória.

Durante a implementação, a arquitetura foi evoluída através da criação de uma interface genérica Repository<T> e da classe abstrata InMemoryRepository<T>, permitindo reutilização das operações CRUD comuns entre todos os repositórios e reduzindo duplicação de código.

<img width="4096" height="2249" alt="image" src="https://github.com/user-attachments/assets/8f1c5908-d40a-4cda-93ff-c2059af98aee" />

#### Camada de Serviços (Service)

A camada de serviços concentra toda a lógica de negócio da aplicação.

Suas principais responsabilidades incluem:

- Validação de dados.
- Regras de autenticação do produtor.
- Gerenciamento de propriedades rurais.
- Processamento de informações climáticas.
- Geração de alertas de risco.
- Recomendações agroclimáticas simuladas.
- Gerenciamento do caderno de campo digital.
- Controle das sessões de chat e consultas.

A utilização de interfaces para os serviços permite desacoplamento entre contratos e implementações, seguindo o princípio da Inversão de Dependência (DIP).

<img width="4095" height="2249" alt="image" src="https://github.com/user-attachments/assets/063d2b5e-ceb7-4330-aad3-cd9b1021bc03" />

#### Camada de Apresentação (View)

A camada de apresentação é composta pelos menus executados via terminal.

Os menus foram organizados por contexto funcional:

- LoginMenu: autenticação e cadastro de produtores.
- MainMenu: navegação principal da aplicação.
- AgroclimaticMenu: consultas climáticas e alertas.
- ManagementMenu: gerenciamento do caderno de campo.
- ChatMenu: interação com o módulo de assistência agroclimática.
  
<img width="4096" height="2249" alt="image" src="https://github.com/user-attachments/assets/f5d6fd0c-4fa6-42d5-b6db-3148fd787240" />

---

## Equipe

| Nome |
|---|
| Julia Vitoria da Luz 
Rafael Costa Dos Santos
Murilo Mendes Rodrigues 
Michael Stephan Silva Gebenlian 
Alvaro Gama Bastos Rangel |

> **Global Solution 2026/1 · FIAP **
