package br.ueg.madamestore.comum.util;

public class UserAccountControl {

    public static final long SCRIPT = 0x0001L;
    public static final long ACCOUNTDISABLE = 0x0002L;
    public static final long HOMEDIR_REQUIRED = 0x0008L;
    public static final long LOCKOUT = 0x0010L;
    public static final long PASSWD_NOTREQD = 0x0020L;
    public static final long PASSWD_CANT_CHANGE = 0x0040L;
    public static final long ENCRYPTED_TEXT_PWD_ALLOWED = 0x0080L;
    public static final long TEMP_DUPLICATE_ACCOUNT = 0x0100L;
    public static final long NORMAL_ACCOUNT = 0x0200L;
    public static final long INTERDOMAIN_TRUST_ACCOUNT = 0x0800L;
    public static final long WORKSTATION_TRUST_ACCOUNT = 0x1000L;
    public static final long SERVER_TRUST_ACCOUNT = 0x2000L;
    public static final long DONT_EXPIRE_PASSWORD = 0x10000L;
    public static final long MNS_LOGON_ACCOUNT = 0x20000L;
    public static final long SMARTCARD_REQUIRED = 0x40000L;
    public static final long TRUSTED_FOR_DELEGATION = 0x80000L;
    public static final long NOT_DELEGATED = 0x100000L;
    public static final long USE_DES_KEY_ONLY = 0x200000L;
    public static final long DONT_REQ_PREAUTH = 0x400000L;
    public static final long PASSWORD_EXPIRED = 0x800000L;
    public static final long TRUSTED_TO_AUTH_FOR_DELEGATION = 0x1000000L;
    public static final long PARTIAL_SECRETS_ACCOUNT = 0x04000000L;

    private long value;

    public UserAccountControl(long value) {
        this.value = value;
    }

    public boolean has(long feature) {
        return (this.value & feature) > 0;
    }

    public void add(long feature) {
        if (!has(feature)) {
            this.value += feature;
        }
    }

    public void remove(long feature) {
        if (has(feature)) {
            this.value -= feature;
        }
    }

    public long getValue() {
        return value;
    }
}