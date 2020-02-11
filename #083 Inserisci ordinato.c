/*
 * Jacopo Del Granchio
 * #083 11.01.2020
 */

 #include <stdlib.h>
 #include <stdio.h>
 #include <math.h>
 #include <locale.h>
 #include <stdarg.h>
 #include <stdbool.h>
 #include <time.h>

// Macro
 #define chiedi(msg, format, ...) \
  printf(msg); \
  scanf(format, __VA_ARGS__);

// Prototipi
void scarica(int v[], int n);
void inserisci(int new, int v[], int *n);
void shiftDx(int v[], int start, int end);

// Funzioni
int main() {
  setlocale(LC_ALL, "");

  const int MAX = 10;
  int n = 0, s, v[MAX];

  do {
    chiedi("Inserire il valore da inserire: ", "%d", &s);
    inserisci(s, v, &n);
    scarica(v, n);
  } while (n < MAX);

  // getchar();
  // system("pause");
  return 0;
}

void shiftDx(int v[], int start, int end) {
  for (int i = start; i > end; i--)
    v[i] = v[i - 1];
}

void inserisci(int new, int v[], int *n) {
  int i;

  for (i = 0; i < *n; i++)
    if (v[i] > new) {
      shiftDx(v, *n, i);
      break;
    }

  v[i] = new;
  *n += 1;
}

void scarica(int v[], int n) {
  printf("[ ");

  for (int i = 0; i < n; i++)
    printf("%d%s", v[i], i != n - 1 ? ", " : "");

  printf(" ]\n");
}
