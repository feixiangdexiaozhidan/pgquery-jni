#include <jni.h>
#include "com_libpg_pgquery_PgQuery.h"
#include "/home/zzw/libpg/libpg_query/pg_query.h"

JNIEXPORT jstring JNICALL Java_com_highgo_pgquery_PgQuery_parse
  (JNIEnv *env, jclass clazz, jstring jsql) {

    const char *sql = (*env)->GetStringUTFChars(env, jsql, 0);

    PgQueryParseResult result = pg_query_parse(sql);

    (*env)->ReleaseStringUTFChars(env, jsql, sql);

    if (result.error) {
        jstring err = (*env)->NewStringUTF(env, result.error->message);
        pg_query_free_parse_result(result);
        return err;
    }

    jstring json = (*env)->NewStringUTF(env, result.parse_tree);
    pg_query_free_parse_result(result);

    return json;
}
