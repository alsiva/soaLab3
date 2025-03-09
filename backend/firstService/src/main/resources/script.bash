# =======================================
# Часть 1: Создание сертификатов для Java
# =======================================
#Создание приватного ключа JAVA
openssl genpkey -algorithm RSA -out private_key.pem

#Создание keystore JAVA
openssl pkcs12 -export -out keystore.p12 -inkey private_key.pem -name changeit

#Создание trustStore JAVA
keytool -importcert -trustcacerts -file ca.crt -keystore keystore.p12 -storetype PKCS12 -alias changeme

# =======================================
# Часть 2: Создание сертификатов для Consul
# =======================================

# Параметры для Consul
CONSUL_CERT_DIR="./CONSUL_CERTIFICATE"
CONSUL_CERTFILE="$CONSUL_CERT_DIR/consul-cert.pem"
CONSUL_KEYFILE="$CONSUL_CERT_DIR/consul-key.pem"

# Создание директории для сертификатов Consul, если её нет
mkdir -p "$CONSUL_CERT_DIR"

# Генерация приватного ключа (PEM) для Consul
echo "Часть 2: Генерация сертификатов для Consul в формате PEM..."

# Генерация приватного ключа для Consul
openssl genpkey -algorithm RSA -out "$CONSUL_KEYFILE" -pkeyopt rsa_keygen_bits:$KEYSIZE

# Генерация публичного сертификата (PEM) для Consul из приватного ключа
openssl req -new -x509 -key "$CONSUL_KEYFILE" -out "$CONSUL_CERTFILE" -days $VALIDITY -subj "/CN=Consul, OU=Dev, O=MyCompany, L=City, ST=State, C=RU"

echo "Сертификаты для Consul созданы: $CONSUL_CERTFILE и $CONSUL_KEYFILE"

# =======================================
# Часть 3: Экспорт сертификатов Java в truststore для Consul
# =======================================

# Параметры для truststore Consul
CONSUL_TRUSTSTORE="$CONSUL_CERT_DIR/consul-truststore.jks"
CONSUL_TRUSTSTOREPASS="truststorepassword"

# Проверка существования сертификата Java
if [ ! -f "$JAVA_CERTFILE" ]; then
    echo "Файл сертификата Java $JAVA_CERTFILE не найден. Пожалуйста, выполните первую часть скрипта."
    exit 1
fi

# Добавление сертификата Java в truststore для Consul
echo "Часть 3: Экспорт сертификатов Java в truststore для Consul..."

# Проверяем, существует ли truststore для Consul
if [ ! -f "$CONSUL_TRUSTSTORE" ]; then
    echo "Truststore для Consul не найден. Создаем новый truststore."
    keytool -import -file "$JAVA_CERTFILE" -keystore "$CONSUL_TRUSTSTORE" -alias "myapp" -storepass "$CONSUL_TRUSTSTOREPASS" -noprompt
else
    echo "Добавляем сертификат Java в существующий truststore для Consul."
    keytool -import -file "$JAVA_CERTFILE" -keystore "$CONSUL_TRUSTSTORE" -alias "myapp" -storepass "$CONSUL_TRUSTSTOREPASS" -noprompt
fi

echo "Сертификат Java добавлен в truststore для Consul."

# Выводим информацию о содержимом truststore для Consul
echo "Информация о содержимом truststore для Consul:"
keytool -list -keystore "$CONSUL_TRUSTSTORE" -storepass "$CONSUL_TRUSTSTOREPASS"


# =======================================
# Часть 4: Экспорт сертификатов Consul в truststore для Java
# =======================================

# Параметры для truststore Java
JAVA_TRUSTSTORE="$JAVA_CERT_DIR/java-truststore.jks"
JAVA_TRUSTSTOREPASS="truststorepassword"

# Проверка существования сертификата Consul
if [ ! -f "$CONSUL_CERTFILE" ]; then
    echo "Файл сертификата Consul $CONSUL_CERTFILE не найден. Пожалуйста, выполните вторую часть скрипта."
    exit 1
fi

# Добавление сертификата Consul в truststore для Java
echo "Часть 4: Экспорт сертификатов Consul в truststore для Java..."

# Проверяем, существует ли truststore для Java
if [ ! -f "$JAVA_TRUSTSTORE" ]; then
    echo "Truststore для Java не найден. Создаем новый truststore."
    keytool -import -file "$CONSUL_CERTFILE" -keystore "$JAVA_TRUSTSTORE" -alias "consul" -storepass "$JAVA_TRUSTSTOREPASS" -noprompt
else
    echo "Добавляем сертификат Consul в существующий truststore для Java."
    keytool -import -file "$CONSUL_CERTFILE" -keystore "$JAVA_TRUSTSTORE" -alias "consul" -storepass "$JAVA_TRUSTSTOREPASS" -noprompt
fi

echo "Сертификат Consul добавлен в truststore для Java."

# Выводим информацию о содержимом truststore для Java
echo "Информация о содержимом truststore для Java:"
keytool -list -keystore "$JAVA_TRUSTSTORE" -storepass "$JAVA_TRUSTSTOREPASS"