Este é um programa escrito em Java, que utiliza um BD  SQL para armazenar seus dados. 
O programa categoriza compras por armazenamento (geladeira e armario), guardando também
informações de: nome, validade, ID (auto increment), e quantidade do item.
Permitindo ao usuário visualizar, adicionar e remover itens tanto da lista completa quanto
de cada local de armazenamento, podendo ainda editar os itens (inclui a possiblilidade de
"mover" um item do armario para a geladeira e vice-versa editando seu atributo categoria.
O projeto utiliza uma arquitetura bem definida separando arquivos de classes modelo, 
mais voltados a aplicação (DAO) e GUI (Jframes vistos pelo usuário que possuem os componentes
com os quais o mesmo interage).
Com objetivo geral de facilitar na organização, o programa pode ainda ser aprimorado para
tratar separadamente itens fora da validade.
O código em java foi baseado nos projetos CanetaApp e Loja vistos na disciplina de POO.
Já o script SQL foi herdado da ideia inicial de aplicativo de despensa da colega Naiara Tavares,
mantendo características originais desta ideia como o uso de ENUM para Categoria ao invés de
ForeignKey para uma tabela de categorias.
