# Instagram-api

Uma API que utiliza Spring WebFlux, Spring Data Reactive MongoDB, Spring Security e JWT. 
### Ambiente para rodar a API: 

- Se for rodar na máquina local: 
1. Instale o mongoDB
2. Baixe algum cliente("Robomongo","MongoDB Compass") para acessar o mongoDB e crie o database: **instagram**

Caso for utilizar um mongoDb que não seja local, mudar a seguinte configuração no **application.properties**:
```
spring.data.mongodb.uri=mongodb://localhost:27017/instagram
```

- Se quiser rodar com docker, você vai precisar: 
1. Instalar o docker
2. Instalar o docker-compose

Para subir a aplicação com o docker, rode o seguinte comando: 

```
$ sudo docker-compose up  
```
<a href="https://insta-api-collection.s3.us-east-1.amazonaws.com/Instagram-API.postman_collection.json?response-content-disposition=attachment&X-Amz-Security-Token=IQoJb3JpZ2luX2VjEE8aCXNhLWVhc3QtMSJIMEYCIQDppnjRpJbTj%2FdKSwKvFKTOiRGz3gaQeO4%2BXecuX23tygIhAL60HhHVr99oYJVK0MZIpyVG5IotCXjIJWR2BEMYW7RJKsQCCEgQABoMNDI1NDY5MzkzNjEzIgyvBgisZ7Ualo18Z5AqoQJPQO9MTIS2ehGQy7LfW2%2B0u0sEwoNTxodeBaeHSkDWyZwTB6iZ2I17mO0wvqnbsuB%2FzllJW%2F1iTkkvnb8GheDWkOQ2SA1D%2Ftudhi2TqLl3wRwWc8BS98aJ4o6eIcKaWTtAzlu1a%2Fjyv8r0c09DS34fsko%2BPn9XqilcKk9XqlVOBRs69mBG0FZ5kx6C%2Bl3YiM6Ct%2BWjrDvLgV%2BGGRaR2K0unQG%2FvB1U5V5AGbkQi6S%2B2%2FKBqT1J350KVlnVS53XNTmtSjlagPfBwCM9Z8w9yjIFcbBytLiXs%2FCk%2BmYZsE7GF7%2B7jKq2xH1y6qg5uv6qM7OWSxPkQ1xBLdyNddHgyllFwDgaRN1iocpBUb%2BNJx4F63WKoXtNcZPMggvEF%2F9%2B6U2EMPT%2BrvoFOq8C8%2FxyXQ1lPSGmpu7I%2BqmmZS%2Bims9XuC3%2By7LRkgkN9T4CRpuwRnvaqR6f5SMt744Rmj4FIlwqekdzdcX6NCuBaABR6tdO8H6pCQq102gqsqoZ%2FVBA1edYYqmSRE9z8fBcFu99bEquvDJSizRVg7UjBsFwvo0tp%2FvDsFXjQH7cO3jSX38QdjE%2BN7TAUg2g%2BB8Oupihr5emZZMuXPkaaeW%2Fn1xEXfrTXuRmoTHP6yYNCZB3zKDx3lmUZuHTgPq2xMUK3Nx0U%2Fb2VYTHErw2ytBJrMjUKdycLZvRatvXA8YX1hgybv5sFZSFq7H%2FzRiPKqdUihHXyTWhYvFuX5Pft6JUui4FtBjDOsJ0tLLnlFXwyZVgnEEkO6tFXM28TFuUJBRMoOKdDBX939BpiUgAcOyw&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20200830T202527Z&X-Amz-SignedHeaders=host&X-Amz-Expires=300&X-Amz-Credential=ASIAWGD7TXLGSVEGOWXX%2F20200830%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Signature=a9e4bfe01192f999678611a78a4c4159a19cce41d09488b54a3bab5f28059bbc"> Collection para Postman da API</a>

### Configuração: 

Essa API está configurada para salvar as imagens da publicação localmente. Mas ela possui configuração para salvar as midias na nuvem, em um bucket da AWS, utilizando o serviço S3.
As seguintes propriedades determina o nome das pastas locais que serão criadas:
```
directory.image-media.name=instagram-media/images
directory.video-media.name=instagram-media/video
directory.profile-media.name=instagram-media/profile
```
 
Para utilizar o armazenamento na nuvem, é necessario mudar as seguintes propriedades:
- Dentro do aplication.properties: 
```
aws-cloud-s3-access-key.value=ChangeValue
aws-cloud-s3-secret-key.value=ChangeValue
aws-cloud-s3-region-code.value=ChangeValue

aws-cloud-s3-bucket-image.value=ChangeValue
aws-cloud-s3-bucket-video.value=ChangeValue
aws-cloud-s3-bucket-profile.value=ChangeValue
```
- Dentro do package **config** na classe **AmazonConfig**: 
Remover os comentários do **@Bean**, ficando assim: 
```
    @Bean
    public BasicAWSCredentials basicAWSCredentials(){
        return new BasicAWSCredentials(ACCESS_KEY,SECRET_KEY);
    }

    @Bean
    public AmazonS3 amazonS3(){
        return AmazonS3ClientBuilder
                .standard()
                .withRegion(REGION)
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials())).build();
    }
```
- No classe **SaveMediaAwsS3Impl**, que está dentro do package **service.impl** remover os seguintes comentarios: 
```
//@Primary

//@Autowired
private AmazonS3 amazonS3;
```
- E por fim, colocar um comentário na seguinte linha da classe **SaveMediaLocalImpl**, que está dentro do package **service.impl**: 
```
@Primary
```

### Estrutura da API
Ela conta com a seguinte estrutura: 

- config
- model
    - domain
    - DTO
    - entity
    - form
    - request
- repository
- resource
- security
- service
    - impl

### Funcionalidades:

1. Usuário
2. Permissões
3. Publicação
4. Perfil
5. Relacionamento
6. Likes
7. Comentários
8. Feed
9. Login






