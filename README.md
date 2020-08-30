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
<a href="https://github.com/francomatheus/collection-instagram-api"> Collection para Postman da API</a>

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






