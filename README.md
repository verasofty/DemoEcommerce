# DemoEcommerce

# Change List :green_book:

Version | Autor | Fecha | Descripcion
--------|---------------------|------------|----------------
1.0 | Judá Escalera| 2021-07-24 | Version inicial

# Integración con el proyecto

## Configuración

Procedimiento

1. Declarar el token de uso del lector en el archivo [gradle.properties](/gradle.properties)

```java
authToken=[Este valor será entregado por KashPay vía correo]
```

2. Agregar el repositorio donde se encuentra el componente AAR de KashPay, esto debe realizarse en el archivo
[build.gradle](/build.gradle)

```java
maven { url "https://jitpack.io"
credentials { username authToken }
}
```

3. Agregar las referencias en el archivo [build.gradle](/app/build.gradle) de la **aplicación**

```java
implementation 'com.github.verasofty:ConnectorLib:v1.0.8'
implementation 'com.github.verasofty:mpos:v1.0.0'
```

4. La URL del ambiente se debe cargar en un preference, de la siguiente forma.

```java
import com.sf.connectors.ISwitchConnector;
```

invocar el siguiente método (copiarlo al proyecto)

```java
private void setServiceURL() {
SharedPreferences prefs;
prefs = PreferenceManager.getDefaultSharedPreferences(thisActivity.getApplicationContext());

 prefs.edit().putString(ISwitchConnector.SHARED_PREFERENCES_URL, thisActivity.getResources().getString(R.string.DEFAULT_URL))
.commit();
}
```

5. Establecer el ambiente (Producción o Pruebas) de operación

```java

import com.onsigna.domain.AuthenticateData;
```

```java
// Variables que se deben configurar.
private final String APLICATION_SECRET = "qs4qa1ralmgb4cna";
private final String APLICATION_KEY = "8z00pj9qxh3vaaggo7lfyw2xkj3rv80c7o1u";
private final String APLICATIONBUNDLE = "test.api.service"; // <-- Ambiente de Pruebas

..

 private void setAuthenticateData() {
Log.d(TAG, "== setAuthenticateData() ==");

AuthenticateData.applicationBundle = APLICATIONBUNDLE;
AuthenticateData.applicationKey = APLICATION_KEY;
AuthenticateData.applicationSecret = APLICATION_SECRET;
}
```

6. Implementar la interfaz CardDataCallBack.
    Esta interface es utilizada para recibir los resutados de la transaccion.

```java

import com.onsigna.mposlib.CardDataCallback;;
```

```java
public class MainActivity extends AppCompatActivity implements CardDataCallback

```
7. En este método llegarán los datos de respuesta de la venta, en un objeto de **TransactionDataResult**
```java
    @Override
    public void onCompletedTransaction(TransactionDataResult tdr) {
        Log.i(TAG, "== onCompletedTransaction() ==");

        if (tdr.getResponseCode() == 0) {
            Toast.makeText(getApplicationContext(), "¡¡¡Venta exitosa!!!\nNúmero de autorización -> " + tdr.getAuthorizationNumber(), Toast.LENGTH_LONG).show();
            Log.i(TAG, "AuthorizationNumber --> " + tdr.getAuthorizationNumber() );
        } else {
            Toast.makeText(getApplicationContext(), "Descripción Error -> " + tdr.getResponseCodeDescription(), Toast.LENGTH_LONG).show();
            Log.i(TAG, "Error --> " + tdr.getResponseCodeDescription() );
        }

    }
    ```
private final String APLICATION_SECRET = "qs4qa1ralmgb4cna";
