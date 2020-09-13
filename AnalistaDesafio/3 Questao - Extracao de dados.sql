-- ******************************************************************************************************************
-- ***** PARTE 1
-- ***** Criar uma tabela em memória dos sistemas do usuário, que agrupe por cpf todos os sistemas do 
-- ***** determinado usuário; 
-- ******************************************************************************************************************
DROP TABLE IF EXISTS USUARIO_SISTEMA_TEMP;

SELECT 
	U.CPF, S.nome AS SISTEMA
INTO 
	USUARIO_SISTEMA_TEMP
FROM 
	USUARIOSISTEMA US
INNER JOIN 
	USUARIO U ON U.ID = US.USUARIO_ID
INNER JOIN 
	SISTEMA S ON S.ID = US.SISTEMA_ID
GROUP BY 
	U.CPF, S.nome
ORDER BY 
	U.CPF;

SELECT * FROM USUARIO_SISTEMA_TEMP


-- ******************************************************************************************************************
-- ***** PARTE 2
-- ***** Retornar todos os usuários do sistema com o CPF (com máscara), o nome do usuário em caixa alta, 
-- ***** bem como os cargos, orgãos e sistemas associados (se existir);
-- ******************************************************************************************************************
SELECT 
	substr(U.CPF,1,3)||'.'||substr(U.CPF,4,3)||'.'||substr(U.CPF,7,3)||'-'||substr(U.CPF,10,2) AS CPF,
    UPPER(U.NOME) AS NOME_USUARIO,
    C.DESCRICAO AS CARGO,
    O.DESCRICAO AS ORGAO,
    S.NOME AS SISTEMA
FROM 
	USUARIO U
INNER JOIN 
	CARGO C ON C.ID = U.CARGO_ID
INNER JOIN 
	ORGAO O ON O.ID = U.ORGAO_ID
LEFT JOIN 
	USUARIOSISTEMA US ON US.USUARIO_ID = U.ID
LEFT JOIN 
	SISTEMA S ON S.ID = US.SISTEMA_ID;