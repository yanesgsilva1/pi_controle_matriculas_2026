🎓 EduControl - Sistema de Controle de Matrículas

> Projeto Integrador — Desenvolvimento de Aplicações Web  
> Curso de Análise e Desenvolvimento de Sistemas (ADS)

---

## 📋 Sobre o Projeto

O **EduControl** é um sistema web para gerenciamento e controle de matrículas acadêmicas. Desenvolvido como Projeto Integrador do curso de ADS, a aplicação permite o cadastro e a gestão de pessoas (alunos/professores) por meio de uma interface web integrada a banco de dados relacional.

---

## 🚀 Tecnologias Utilizadas

| Camada | Tecnologia |
| --- | --- |
| Linguagem | Java |
| Framework Web | JavaServer Faces (JSF) |
| Persistência | Hibernate (JPA) |
| Banco de Dados | MySQL |
| Frontend | HTML5, CSS3, XHTML |
| IDE | Apache NetBeans |
| Servidor | GlassFish |
| Build | Maven |

---

## 📁 Estrutura do Projeto

```
controle-matriculas-educontrol/
├── src/
│   └── main/
│       ├── java/
│       │   └── controle/matriculas/educontrol/
│       │       ├── bean/          # Managed Beans (JSF)
│       │       ├── model/         # Entidades JPA
|       |       ├── dao/           # Persistência/Operações do CRUD
│       │       └── util/          # Classes utilitárias (HibernateUtil)
│       ├── resources/
│       │   └── hibernate.cfg.xml  # Configuração do Hibernate
│       └── webapp/
│           ├── cadastroPessoa.xhtml
│           ├── menuPrincipal.xhtml
│           ├── login.xhtml
│           └── resources/
│               └── css/           # Estilos das páginas
└── controle_matriculas.sql        # Script de criação do banco de dados
```

---

## ⚙️ Pré-requisitos

Antes de executar o projeto, certifique-se de ter instalado:

- [Java JDK 8+](https://www.oracle.com/java/technologies/downloads/)
- [Apache NetBeans](https://netbeans.apache.org/)
- [GlassFish Server 5](https://javaee.github.io/glassfish/)
- [MySQL Server](https://dev.mysql.com/downloads/)
- [Maven](https://maven.apache.org/)

---

## 🗄️ Configuração do Banco de Dados

1. Acesse seu cliente MySQL (MySQL Workbench, DBeaver, etc.)
2. Execute o script de criação do banco:

```sql
source controle_matriculas.sql
```

3. Abra o arquivo `src/main/resources/hibernate.cfg.xml` e atualize as credenciais com os dados do seu ambiente local:

```xml
<property name="hibernate.connection.url">jdbc:mysql://localhost:3306/controle_matriculas</property>
<property name="hibernate.connection.username">seu_usuario</property>
<property name="hibernate.connection.password">sua_senha</property>
```

> ⚠️ **Atenção:** Nunca envie suas credenciais reais para o repositório. Certifique-se de que o `hibernate.cfg.xml` com usuário e senha do seu banco local **não seja versionado** — adicione-o ao `.gitignore` se necessário.

---

## 🐟 Configuração do GlassFish no NetBeans

### 1. Baixar e instalar o GlassFish

- Acesse [https://javaee.github.io/glassfish/](https://javaee.github.io/glassfish/) e baixe o **GlassFish 5**
- Extraia o arquivo em um diretório de fácil acesso, por exemplo: `C:\glassfish5`

### 2. Registrar o servidor no NetBeans

1. No NetBeans, vá em `Tools > Servers`
2. Clique em **Add Server...**
3. Selecione **GlassFish Server** e clique em **Next**
4. Em *Installation Location*, aponte para a pasta onde extraiu o GlassFish (ex: `C:\glassfish5`)
5. Clique em **Next** e depois em **Finish**

### 3. Associar o GlassFish ao projeto

1. Clique com o botão direito no projeto no NetBeans > **Properties**
2. Vá em **Run**
3. Em *Server*, selecione o **GlassFish Server** que você registrou
4. Clique em **OK**

### 4. Iniciar o servidor

- Vá na aba **Services** (Janela > Services)
- Expanda **Servers**, clique com o botão direito em **GlassFish Server** > **Start**
- O console de administração ficará disponível em: `http://localhost:4848`

> 💡 **Dica:** Se aparecer erro de porta ocupada, verifique se outra instância do GlassFish ou Tomcat já está rodando.

---

## ▶️ Como Executar

1. **Clone o repositório:**
  
  ```bash
  git clone https://github.com/yanesgsilva1/pi_controle_matriculas_2026.git
  ```
  
2. **Abra no NetBeans:**
  
  - Vá em `File > Open Project`
  - Navegue até a pasta `OneDrive/Documentos/NetBeansProjects/controle-matriculas-educontrol`
3. **Configure o banco de dados** conforme a seção acima
  
4. **Configure o GlassFish** conforme a seção acima
  
5. **Execute o projeto** com `Run > Run Project (F6)`
  
6. Acesse no navegador: `http://localhost:8080/controle-matriculas-educontrol`
  

---

## 🖥️ Funcionalidades

- [x] Tela de Login
- [x] Tela Menu Principal
- [x] Cadastro de Pessoas
- [ ] Cadastro de Disciplinas *(em desenvolvimento)*
- [ ] Cadastro de Matrículas *(em desenvolvimento)*
- [ ] Relatório de Faturamento por Período *(em desenvolvimento)*
- [ ] Relatório de de Disciplina Por Professor *(em desenvolvimento)*
- [ ] Relatório de Disciplina por Aluno *(em desenvolvimento)*

---

## 👩‍💻 Autores

Desenvolvido por estudantes do curso de **Análise e Desenvolvimento de Sistemas**.

---

## 📄 Licença

Este projeto foi desenvolvido para fins acadêmicos.
