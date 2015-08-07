package com.doctorandonuts.taskwarrior.sync;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.security.cert.CertificateException;
import java.text.ParseException;

/**
 * Created by jgowing on 7/28/2015.
 */
public class TaskWarriorSync {

    private static final String TAG = "TaskWarriorSync";

    String Andonuts_key = "-----BEGIN RSA PRIVATE KEY-----\n" +
            "MIIJKAIBAAKCAgEA7ozBJ3GXNRezZBQqqJF2K18X4+5sTN0LgxWYySvKc51V13W3\n" +
            "tEWPvK+LLok86s4MpKEZ+OOkf9c6i+z2b7QAFR0edg84waddED5098+jC71t+px+\n" +
            "GR0ofQR6sBHmm7KRaUDjEfXz+myYcA9go7bsVqFI5KbbC7qAKiPQNRP0D/Lrjg4O\n" +
            "Tq0DxUAX99pEHxmggaTsdaAZXT6ZdCEU8ByLSHKoQr2HtAuTmiMCWGNMtuXNnPpp\n" +
            "ozg5hXAcxRm8oFbEj7q1cdA7Vw6U0ezcurl8QLMMbbYj96PEIGJA13O3mJZvhG29\n" +
            "sraJYFkvtC0T3Ok/mAGC5AkELgeQ55wXRnMxM9/V/ngfuLhpVvVK0b2FCXLywuKp\n" +
            "TNYgystp8UPn6IYEU6taVKZg42j6b4Al3GomUGLaUCZ7bt+ns+ViMZawx7HiOk0d\n" +
            "+5ikPiUrvRhIwGVYvdVQA+lF1ny5LY0wQQ43sAykOoIcgMaYEBzGyupx8Al4UM/6\n" +
            "l6NvntGqBDes+a0eRzwoZeLgKv20Tjz01PRVhhrmmsOqlLgtYpXzjH5MB1v7CTAO\n" +
            "Rwo22xsO6yE5dlrKX27/qezGmGHIKfirYZPlLIrb2VEIoU+FcnKK7AVbE0B8EzHv\n" +
            "ZmSPkeN6iA8LkxvNLWa6pAi6QhnHgvfnPnz+/l8kYjfT21FxVY3KYlNpir0CAwEA\n" +
            "AQKCAgA6Qrox7fYmC2HOwXYhsVURBqqJCoP8ycez/iRQjZdmYTjD5IY3Sv3t8Vmm\n" +
            "xfHQzv4bfxUeyko0xtYPspIFegrDHVeBMfqEoWGDRB3BID8L8PnZSTsq2wo2bk+g\n" +
            "jN6rjBMjh6ttiYpK0Rbx12+czGIwTjfFUFMTLDt9Dz4cmID1HDkXeqtX372aAStZ\n" +
            "Zz9X8SRYRXVSzQUk1tMXn7TDZ9VEnmwl0LBB8snbzU0XVTk5OirbefUu3d5+joEY\n" +
            "Sk7dQhPDnrAN2fOEMZV7J8RI/mAwVQpmCSQDqUWPOy/m7uYaKDOYPk0kRpyKoX2b\n" +
            "HZ9KRtU3RLc5BlK1AzgKuP6eQCf1sKvsYqCtT3tDN2MZJ2alhtBsCp43t4KI4CaR\n" +
            "0qeOtha7Sd6bwRiv3HIjcPlRhNhpqqubHG+a7pWfq46Ehes9l6cUlV2jEYgv6To1\n" +
            "+aEDPH9dokEOtLW7+FJucLImPSE7h071r441tVDnDfKlSXcYphDg88p0tA039hPS\n" +
            "ZlLS8uWh19H7wwuWKi8e04VApbZmShx20kNdDJ3tAEH1fbqpezrsbEJEsxY5ul00\n" +
            "GvydQ5cYNLiLPAs8e46XAQf1ooq50eTZnkWrDgXWXUcbFn6Sgn9UeoacZhrsYLpj\n" +
            "tI1zaRHu0w3XN5V9PPeTDKbh5/OIeylF9Uku0J/Y5M4Z/ZaXgQKCAQEA8m3ej1T+\n" +
            "wzq0Q4x3tR1zQkUI56Ga0ncyqxyVY//bANvRHPvz+hGsr80NradqMTdgQrtisIPX\n" +
            "W1/7Cn/V+CHG3T6pp6hxPASI6ZUHJl5UQELW6eLHi8CHM9LGR6dxqi4lZfn/Mtsn\n" +
            "2XHgNh3MGZ1jxGm8N1YRhbzDtFLGPa1klMiNM7IEkEFAs2UwfjzJNOB4F4n+56P2\n" +
            "qT3//bfFlTmog74rT0Lx2EFH/c8VL78oxu8IDR2KoSXKv7V5IV5Pve/Y67u4nSk6\n" +
            "DqA++u1Z1EMRwBr6xFzh686cwDTNu/V1dAr0Bc7feS1ORLFmxsDLFYoQW7xBvoSZ\n" +
            "yhLpPXMdMHtGDQKCAQEA++dKw7Noe1SoeUeagz3kN69P7Hir8OGpvZiYWCjGUgzh\n" +
            "3uqF6W5zYvAH6wxu3GzVp3EThp8XVnbme6cllatyyiLXbbuF9fRLCr23g9RF3PFm\n" +
            "0FJh7EayxD1FSEak27Z9fszTqQPpAgcOjlBmnXOO+Ft2nN/ivsJvEgU/zCwGfzsi\n" +
            "ALleprpHDySGJMJoRhP4muB6/wSMl9wyzTjm73eHeJ5/6RFvZ1UcHATXorY2ajwT\n" +
            "ieuXZqaFS6gAdxkDuJZplmHOkkvzC8ftAwcX+NUSJm57l9fSGFpM4u6mSPWuO5X0\n" +
            "VSu1HqdUVTeiH0UeTX3Qw+HrVTV41tEDB+d1kj1bcQKCAQAeHkQ+LmMQQiTPF45v\n" +
            "s97z5ru6eztO1nw3AfAVYl4E90o9fZfMA5IerSz73Aa3YpfU8o7KOzbwJTZ9EXC1\n" +
            "yQx5xCUzrRuUR0PzMQ6YV7PD4AnvOLttw1eIurDjigv3BpCi4LIx0URcIWenkYAf\n" +
            "1ovkhPZbMZgvs0nlBzuVwgry6Q+R0pX6jgDJyNKDfG3K4fBJjF1czeQBFrDNBp3H\n" +
            "ckhbRa7/w+Nj6IhXIfkR23/nZx7q8jac2qMbuiH4YU0q3bdnYgUHkW2jD4mDhyVX\n" +
            "15VG9pMc/iUbNKoIvwaoSVB0QEAc0Jy240gMBLYuDxmr7WEm5HaOgs7QahUMC1cf\n" +
            "BNGpAoIBAA3Kh+lW9S1rYR3isa7lwIzb6EBGCwPRFv3uUMji0pjDY8pUhKhXB7Iu\n" +
            "+gAAWkFvI/Na2XrRUvHE6LF0YTZkgJBYf/rd/WON0F3yLQ3mnTyPatWU7Q+Gf1sH\n" +
            "D7f5RiVRRGWgySXDtQoLYNyhwjx0iytFtqS4DS3sbzBJYUjIUEK+9gmcHHZ8VE+b\n" +
            "7Ewu6I6NoVq5V4KGuTvHLnDV4K7TmHMeGlqkPbkmi7ds/HFIEIqYZ2lxoIRaMYoG\n" +
            "1J+wmueMNJOrHygbSF8ovu0fgkx5Pdkky1dw1WvH5dW2wxvTd4qDlzDih7P57UAN\n" +
            "H68elZ4geUGAKOYtW0RHhSb1oRY0jcECggEBAMakli2BLDi0BtI0CVsmyQOTt2VU\n" +
            "yNPNsV9jaWlihDR3ZXg3D1CE2hERDTuxwmU/+5yuz5aC976tP6GYGV1FUvGxgJLN\n" +
            "RbEM7Cj+l/WCOPBtVmdAWEr8TFh/5T7+qakthRGCZB6+7TPnWBrtDXuW4y+nfE1p\n" +
            "Ewqx5/W4arCm4Bi1fq3Fy6Y9dya/8EGnzWHvXbx6i2UPAJ7y9zGZquQRvcRB9SBz\n" +
            "dwxkqBn1DwTTgIZ2bt4M81Rug2RC/NawdNxRCYyka6Y4frMVLe4truO8cxknJrYP\n" +
            "9u0vLQ4BQ6o4IRH1uxuJzPmqThGt+1P2vBlELjQp4cKZxwYrjsebEUu0vDk=\n" +
            "-----END RSA PRIVATE KEY-----\n";
    String Andonuts_cert = "-----BEGIN CERTIFICATE-----\n" +
            "MIIFmjCCA4KgAwIBAgIEVaVkojANBgkqhkiG9w0BAQsFADBsMQswCQYDVQQGEwJV\n" +
            "UzEYMBYGA1UEChMPRG9jdG9yIEFuZG9udXRzMRAwDgYDVQQHEwdMaXZvbmlhMREw\n" +
            "DwYDVQQIEwhNaWNoaWdhbjEeMBwGA1UEAxMVYW5kb251dHMuYXN1c2NvbW0uY29t\n" +
            "MB4XDTE1MDcxNDE5MzYwMloXDTE2MDcxMzE5MzYwMlowOjEYMBYGA1UEChMPRG9j\n" +
            "dG9yIEFuZG9udXRzMR4wHAYDVQQDExVhbmRvbnV0cy5hc3VzY29tbS5jb20wggIi\n" +
            "MA0GCSqGSIb3DQEBAQUAA4ICDwAwggIKAoICAQDujMEncZc1F7NkFCqokXYrXxfj\n" +
            "7mxM3QuDFZjJK8pznVXXdbe0RY+8r4suiTzqzgykoRn446R/1zqL7PZvtAAVHR52\n" +
            "DzjBp10QPnT3z6MLvW36nH4ZHSh9BHqwEeabspFpQOMR9fP6bJhwD2CjtuxWoUjk\n" +
            "ptsLuoAqI9A1E/QP8uuODg5OrQPFQBf32kQfGaCBpOx1oBldPpl0IRTwHItIcqhC\n" +
            "vYe0C5OaIwJYY0y25c2c+mmjODmFcBzFGbygVsSPurVx0DtXDpTR7Ny6uXxAswxt\n" +
            "tiP3o8QgYkDXc7eYlm+Ebb2ytolgWS+0LRPc6T+YAYLkCQQuB5DnnBdGczEz39X+\n" +
            "eB+4uGlW9UrRvYUJcvLC4qlM1iDKy2nxQ+fohgRTq1pUpmDjaPpvgCXcaiZQYtpQ\n" +
            "Jntu36ez5WIxlrDHseI6TR37mKQ+JSu9GEjAZVi91VAD6UXWfLktjTBBDjewDKQ6\n" +
            "ghyAxpgQHMbK6nHwCXhQz/qXo2+e0aoEN6z5rR5HPChl4uAq/bROPPTU9FWGGuaa\n" +
            "w6qUuC1ilfOMfkwHW/sJMA5HCjbbGw7rITl2Wspfbv+p7MaYYcgp+Kthk+UsitvZ\n" +
            "UQihT4VycorsBVsTQHwTMe9mZI+R43qIDwuTG80tZrqkCLpCGceC9+c+fP7+XyRi\n" +
            "N9PbUXFVjcpiU2mKvQIDAQABo3YwdDAMBgNVHRMBAf8EAjAAMBMGA1UdJQQMMAoG\n" +
            "CCsGAQUFBwMCMA8GA1UdDwEB/wQFAwMHoAAwHQYDVR0OBBYEFGk9x2s88bX9BJ6E\n" +
            "RIQAwDYi622SMB8GA1UdIwQYMBaAFFCOdiPLhYP/2JJNSy2MlWrPvbJ+MA0GCSqG\n" +
            "SIb3DQEBCwUAA4ICAQCCeEZs/Go11Fr9++keojFvB4ICi4cdMLlgUXS18cquaaPk\n" +
            "7KZExRJPkwNGniRtScuc3cdSxDJZsH51tTDl0AV/Gs24KzLjXWg8yrgOLtrB1Zia\n" +
            "fRU9WcOj4g9TybjRZK18xmbRNa9pNVg+V0Ub3a3NSLrvpIeu8g3TxBaoQH7E1LjY\n" +
            "6l2bZl/kaYycVLBQQbkNJNWcah8YY/dgRpovC7pBNnwBRJIB1BOQoun5IDZWbVeB\n" +
            "y1k/I8iEJELKcDTlI+on6aY5AcQfpSoG7VSna70yTLU9LHpvLBjJRH6cPEww4Q/f\n" +
            "p7S/fZrKxKtIshSsYekq+iKkOxAcPKOwHrzK3te7Q5t2uzoonxrW+hFfRzPQOAsP\n" +
            "556M5jMlroip+Lb0Z+qpnwcnBUQHHigY8Z7omO9Q+7zT9nrrcnxzyns73fSpLkzS\n" +
            "D/wd+QkVfSxltGtnCWRVdIiUD9MsivxmHucV5/uQ48vxM99ssv8XCBGmMWT30oax\n" +
            "+OGJWQ04/ec7WH4LGfdJpf9U9+zz2VYCPVCkE47UE37e9HTDTJJWrt4o68i4cSt6\n" +
            "1KeNgB/ByOByyB4JINy4Z2XKiQqdOFGrF4RobxaUFXIsbeXWBzuWys2N9N+E3gru\n" +
            "EIjOF4zjtbCzjdTQ3mjQtzvHDpEjs4pHUZVH1pf/QlBy3CRlyU0C9md4Qzhy/A==\n" +
            "-----END CERTIFICATE-----\n";
    String ca_cert = "-----BEGIN CERTIFICATE-----\n" +
            "MIIFmTCCA4GgAwIBAgIEVZHnizANBgkqhkiG9w0BAQsFADBsMQswCQYDVQQGEwJV\n" +
            "UzEYMBYGA1UEChMPRG9jdG9yIEFuZG9udXRzMRAwDgYDVQQHEwdMaXZvbmlhMREw\n" +
            "DwYDVQQIEwhNaWNoaWdhbjEeMBwGA1UEAxMVYW5kb251dHMuYXN1c2NvbW0uY29t\n" +
            "MB4XDTE1MDYzMDAwNDkxNVoXDTE2MDYyOTAwNDkxNVowbDELMAkGA1UEBhMCVVMx\n" +
            "GDAWBgNVBAoTD0RvY3RvciBBbmRvbnV0czEQMA4GA1UEBxMHTGl2b25pYTERMA8G\n" +
            "A1UECBMITWljaGlnYW4xHjAcBgNVBAMTFWFuZG9udXRzLmFzdXNjb21tLmNvbTCC\n" +
            "AiIwDQYJKoZIhvcNAQEBBQADggIPADCCAgoCggIBAMbRKJtKKdltfogAsltJt+bT\n" +
            "CL54U9r4kQKabiADL0+G3F1a8K/plVTzkdyMy0Mw527bcGw1hCXq+9eumrOGcz1w\n" +
            "ks7zyeuo34hRvw4N/1s80Inng6E4dqOVHgjSnBbgrgUlE07aGnYm2B+/abajeZuL\n" +
            "HET11AB6QemK8ha0KJ/lw2r4YpuCE/7LrhLEFdr6Zym7tsXOq0MTt61mD8+EES4k\n" +
            "QQCP3Cf9xcgfPb63PeyT869n5KrKtpMVQ3F3zRfzDLYbLI8QcPFD3E5Nu8BWSxEC\n" +
            "6E/m3wUPIUzeFhiP8m7+jq7ceh/aLqslQ5+aj2tLmCxWkrxpYodwfjfrFOEKKcAE\n" +
            "vA4vjgu/0Z+6OyVhAmPMakZlbkgMuYfrUJqdeRJ4OcC/i+47obbLQdEz4PZB5e1m\n" +
            "soCASChTn+oXLqwzMYprPfEBpyxasDbhzn7GYp062xs416YJtBTbXzEq1hCZJ+wr\n" +
            "YWvmrEriZeaURAAzsr0t7yTroyzh/+SxXFSzGKu55t6aggr4/ECukAZahwUqJcNu\n" +
            "NFoCaKoMPBUqEoAQv4SdRHHADvIH9NQ3UbWXY9gBJKDZyXxkN0xgrZwVtGPxdm5U\n" +
            "c2GHOzcj2pF06umU5PcBWnx+GgN/TNaTyrUtAiELIVnjvEtKDrpNDjuxD1xg0x+o\n" +
            "3DlxLJf0rNGEloIMiIe1AgMBAAGjQzBBMA8GA1UdEwEB/wQFMAMBAf8wDwYDVR0P\n" +
            "AQH/BAUDAwcEADAdBgNVHQ4EFgQUUI52I8uFg//Ykk1LLYyVas+9sn4wDQYJKoZI\n" +
            "hvcNAQELBQADggIBAFLjqOEj6WftH5HWlRhxAbDgs3KOgnnVQrgDtJ3GjsfgKN4E\n" +
            "iiiyvazvWLmO/82dIGCeJQ7bjlXugd6w9tIX82plbfF9MOHfYDtd2JlJ/fg4vGHq\n" +
            "Ha9RHOUcubKSgk5i8o9fPJRXs2HC22iy3AiF3c2ZIS7FLaLc6953C/iuYEuZYskx\n" +
            "jzK/xkOLh9vVwslPTjwjkIkSxEOo337I42e2vtFU26yxcTDxVvOtWChwQuGJKKZa\n" +
            "zWmDY2XclujeV6Ki/QXT915O00ZArFqT9jFjo0H25G4opooXorxMjUrONbDoJ9fy\n" +
            "HhNt5YUnxC2AjehZAYpZA3LCwGo1JEycnVKZA/vJAozUrzbSTXeNSzb7aQ+MdoJB\n" +
            "vNvSrQqLr0Uw8LHy/G4FjkoLuO1/beTgleleRhqsMBrWK7+z4ZmNH2ltrshFhmH9\n" +
            "axGBSf3x4BLDZsLjCur0ebQf58WbmiMh5o0qtJzkIlpmefV6ImWxlf2YozoHxDY8\n" +
            "85/3kyldLXDRQ+kcs/0R4vqq/D6dUSDdPh2aFxwmja98tDy4V4/qaYtaxLcyDcNE\n" +
            "mNHhsN8qR4Q7IHfpuKVrZIJzyfnYPJXLaiiPqzhk7aoGjoLWvd8oFlk71tL43IZk\n" +
            "mko2Fel47NuVyIhSkeQAb3ldTpgrsDjPND4D2/AAHakFdtSk+WgPNS7jdwQa\n" +
            "-----END CERTIFICATE-----\n";




    public String getTaskWarriorData() {
        final Msg sync = new Msg();
        sync.set("protocol", "v1");
        sync.set("type", "sync");
        sync.set("org", "Main");
        sync.set("user", "Doctor Andonuts");
        sync.set("key", "cf5a3fa3-5508-4e28-9497-5b44113d45a8");
        final StringBuilder payload = new StringBuilder();
        sync.setPayload(payload.toString());

        try {
            final TLSClient client = new TLSClient();

            try {
                // Initialize all the TLS and cert info for a connection
                client.init(ca_cert, Andonuts_cert, Andonuts_key);
            } catch (final ParseException e) {
                Log.e(TAG, "cannot open certificate", e);
            } catch (final CertificateException e) {
                Log.e(TAG, "general problem with init", e);
            } catch (final TLSClient.NoSuchCertificateException e) {
                Log.e(TAG, "NoSuchCertificateException", e);
            }

            try {
                // Create TLS connection
                client.connect("andonuts.asuscomm.com", 53589);
            } catch (final IOException e) {
                Log.e(TAG, "cannot create socket" + e.toString(), e);
                client.close();
            }

            client.send(sync.serialize());
            final String response = client.recv();
            client.close();

            final Msg remotes = new Msg();
            try {
                remotes.parse(response);
            } catch (final MalformedInputException e) {
                Log.e(TAG, "cannot parse message", e);
                client.close();
            } catch (final NullPointerException e) {
                Log.wtf(TAG, "remotes.parse throwed NullPointer", e);
                client.close();
            }


            String data = remotes.getPayload();
            return data;
        } catch (Exception e) {
            return e.toString();
        }
    }



}
