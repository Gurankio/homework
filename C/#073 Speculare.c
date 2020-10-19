/*
 * Jacopo Del Granchio
 * #073 18.12.2019
 *
 * Controlla se un vettore è speculare.
 */

 #include <stdlib.h>
 #include <stdio.h>
 #include <math.h>
 #include <locale.h>
 #include <stdarg.h>
 #include <stdbool.h>

// Macro
 #define chiedi(msg, format, ...) \
  printf(msg); \
  scanf(format, __VA_ARGS__);

// Prototipi
void carica(int v[], int n);
void scarica(int v[], int n);
bool speculare(int v[], int n);

// Funzioni
int main() {
  setlocale(LC_ALL, "");

  int n;
  chiedi("Inserire la lunghezza del vettore: ", "%d", &n);

  int v[n];
  carica(v, n);

  printf("Il vettore %sè speculare.\n", speculare(v, n) ? "" : "non ");

  // getchar();
  // system("pause");
  return 0;
}

bool speculare(int v[], int n) {
  bool r = 1;

  for (int i = 0; i < n / 2; ++i)
    if (v[i] != v[n - 1 - i]) r = 0;

  return r;
}

void carica(int v[], int n) {
  for (int i = 0; i < n; i++) {
    printf("Inserisci il %d elemento del vettore: ", i);
    scanf("%d", &v[i]);
  }
}

void scarica(int v[], int n) {
  printf("[ ");

  for (int i = 0; i < n; i++)
    printf("%d%s", v[i], i != n - 1 ? ", " : "");

  printf(" ]\n");
}
