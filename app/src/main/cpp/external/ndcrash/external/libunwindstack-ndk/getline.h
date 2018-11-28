#ifndef GETLINE_H_
#define GETLINE_H_
#include <stdlib.h>
#include <stdio.h>

#ifdef __cplusplus
extern "C" {
#endif

ssize_t getline(char **buf, size_t *bufsiz, FILE *fp);

#ifdef __cplusplus
}
#endif

#endif //GETLINE_H_