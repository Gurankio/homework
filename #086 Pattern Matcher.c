/*
 * Jacopo Del Granchio
 * #086 20.01.2020
 *
 * Lorem ipsum dolor sit amet, consectetur adipisicing elit,
 * sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <locale.h>
#include <stdarg.h>
#include <stdbool.h>
#include <string.h>

#define LENGTH 64

// Macro
#define chiedi(msg, format, ...) \
  printf(msg); \
  scanf(format, __VA_ARGS__);

// Prototipi
int match(char input[LENGTH], char pattern[LENGTH]);

// Funzioni
int main() {
  setlocale(LC_ALL, "");

  char input[LENGTH], pattern[LENGTH];
  printf("Inserire la stringa: ");
  gets(input);
  printf("Inserire la sotto-stringa: ");
  gets(pattern);

  int count = match(input, pattern);
  printf("Il pattern e' stato trovato %d volte.\n", count);

  // getchar();
  // system("pause");
  return 0;
}

int match(char input[LENGTH], char pattern[LENGTH]) {
  int count = 0;

  char *temp = strstr(input, pattern);

  while (temp != NULL) {
    printf("Posizione della sotto-stringa %d -> %d\n", count + 1, (int)temp - (int)&input[0] + 1);
    count++;
    temp = strstr(temp + 1, pattern);
  }

  return count;
}
