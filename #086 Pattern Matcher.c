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

#define LENGTH 64

// Macro
#define chiedi(msg, format, ...) \
  printf(msg); \
  scanf(format, __VA_ARGS__);

// Prototipi
int match(char input[LENGTH], char pattern[LENGTH], int position[LENGTH]);
int len(char *input);

// Funzioni
int main() {
  setlocale(LC_ALL, "");

  char input[LENGTH], pattern[LENGTH];
  printf("Inserire la stringa: ");
  gets(input);
  printf("Inserire il pattern: ");
  gets(pattern);


  int out[LENGTH], count = match(input, pattern, out);
  printf("Il pattern e' stato trovato %d volte.\n", count);

  for (int i = 0; i < count; i++)
    printf("\tPosizione: %d\n", out[i]);

  // getchar();
  // system("pause");
  return 0;
}

int match(char input[LENGTH], char pattern[LENGTH], int position[LENGTH]) {
  int count = 0;

  for (int i = 0; i < len(input) - len(pattern) + 1; i++) {
    int trovato = 1;

    for (int j = 0; j < len(pattern); j++)
      if (input[i + j] != pattern[j]) trovato = 0;

    if (trovato) {
      position[count] = i;
      count++;
    }
  }

  return count;
}

int len(char *input) {
  int i = 0;

  while (input[i] != '\0') i++;

  return i;
}
