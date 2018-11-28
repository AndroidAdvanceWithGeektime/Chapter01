#ifndef DL_ITERATE_PHDR_H_
#define DL_ITERATE_PHDR_H_
#include <link.h>

#if __ANDROID_API__ < 21
int dl_iterate_phdr(int (*__callback)(struct dl_phdr_info*, size_t, void*), void* __data);
#endif /* __ANDROID_API__ >= 21 */

#endif //NDCRASHDEMO_DL_ITERATE_PHDR_H
