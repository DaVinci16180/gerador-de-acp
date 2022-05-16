# Gerador de ACP

A presente aplicação lê arquivos de texto, gera autômatos 
e avalia palavras para saber se pertencem à linguagem.

O arquivo de texto deve estar conforme o modelo presente 
na raiz do projeto (arquivo `modelo.txt`). No mesmo diretório
se encontra também um arquivo de exemplo (arquivo `exemplo.txt`), contendo um
autômato para a linguagem ww<sup>R</sup>. O arquivo de texto
contendo o autômato deve seguir a risca o modelo oferecido.
Caso contrário, o programa não coneguirá gerar o autômato de
forma correta ou de forma nenhuma. Portanto, observe bem os 
arquivos de modelo e de exemplo para gerar seu autômato.

Uma vez gerado o arquivo do autômato, basta executar o programa.
Para isso, navegue até o diretório contendo o arquivo `.jar` (`out/artifacts/gerador_de_acp_jar/gerador_de_acp.jar`),
abra seu terminal de preferencia e digite o seguinte comando:
```
 java -jar gerador_de_acp.jar
```
Após isso, basta seguir as instruções do programa. É recomendável ter o arquivo do automato
no mesmo diretório do `.jar` no momento da execução, pois isso facilitará
na hora de informar o caminho para o arquivo.