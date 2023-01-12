Implementação do pattern CQRS, utilizando mensageria com RabbitMQ e serviço de cache com Memcached.
Arquitetura física da aplicação:
![obj](arquitetura.jpeg)

Design Patterns utilizados: 
- Builder
- Singleton

Programação reativa não bloqueante utilizada também, para enviar a mensagem para a fila.

Arquitetura lógica da aplicação desenvolvida utilizando pattern Ports and Adapters:

Arquitetura lógica na visualização Onion:
![obj](arquitetura-onion.jpeg)

A camada de Ports (Web) irá invocar a camada de Application, que irá tratar os dados recebidos e passar para o CORE.

E a camada de Infra (WriteDatabase, ReadDatabase, Memcached, RabbitMQ) devem seguir um contrato de Interface do CORE, utilizando a Inversão de Dependencia do SOLID.

Dessa forma o CORE (Domain e Use Case) fica protegido, pois os dispositivos de I/O externos dependem do CORE, mas o CORE não depende de nenhum componente externo, é um núcleo completamente isolado.

Inicializar serviços externos:

Verificar se porta está em funcionamento na máquina:
    
    lsof -i tcp:PORT

Startar Stopar Memcached (default port = 11211):

    brew services restart memcached
    brew services stop memcached

Subir containers:

    docker-compose up
Obs: executar na pasta que tem o **docker-compose.yml**