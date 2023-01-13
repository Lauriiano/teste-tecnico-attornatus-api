# Api Desenvolvida para teste de emprego

> Status: Finalizada ☑️

## Endpoints:

> URL_BASE: attornatus/api/pessoa

```bash
# POST
+ URL_BASE/

# Body
* String nome;
* String nascimento;

⚠️ ambos são obrigatórios

#Descrição
* Cadastra uma pessoa

# Retorno
* Pessoa Cadastrada com o id
```

```bash
# GET
+ URL_BASE/

# Body
* Sem Body

#Descrição
* O endereço só é retornado caso tenha algum cadastrado como principal

# Retorno
* Lista de Pessoas Cadastradas
```

```bash
# GET
+ URL_BASE/{id-da-pessoa}

# Body
* Sem Body

#Descrição
* O endereço só é retornado caso tenha algum cadastrado como principal

# Retorno
* Pessoas Cadastrada se existir no banco pelo id
```

```bash
# PUT
+ URL_BASE/{id-da-pessoa}

# Body
* String nome;
* String nascimento;

⚠️ ao menos um é obrigatório

#Descrição
* Edita a pessoa com o id que foi passado.
* caso a pessoa não exista no banco, retorna um erro

# Retorno
* Pessoa atualizada.
```

```bash
# POST
+ URL_BASE/endereco/cadastro/{id-da-pessoa}

# Body
* String cep;
* String logradouro;
* int numero;
* String cidade;

⚠️ TODOS são obrigatório

# Retorno
* Cadastra um endereço para uma pessoa, esse endereço é retornado na resposta da requisição com o id.
```

```bash
# GET
+ URL_BASE/endereco/{id-da-pessoa}

# Body
* Sem Body

# Retorno
* caso exista, retorna o endereço cadastrado para a pessoa com o id informado
```

```bash
# POST
+ URL_BASE/endereco/principal/{id-da-pessoa}

# Body
* String cep;

⚠️ obrigatório

#Descrição
* cadastra um endereço principal para a pessoa com o id informado.
* o endereço precisa está cadastrado e associado a pessoa.
* caso ja tenha algum endereço como principal, esse será substituído.

# Retorno
* Sem Retorno
```
