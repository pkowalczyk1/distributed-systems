import cProfile
import os
import random
import logging
import ray


if ray.is_initialized:
    ray.shutdown()
ray.init(address='ray://172.19.0.2:10001', ignore_reinit_error=True, logging_level=logging.ERROR)


def bubblesort(arr):
    n = len(arr)
    swapped = False
    for i in range(n - 1):
        for j in range(0, n - i - 1):
            if arr[j] > arr[j + 1]:
                swapped = True
                arr[j], arr[j + 1] = arr[j + 1], arr[j]
        if not swapped:
            return arr
    return arr


@ray.remote
def bubble_sort_distributed(arr):
    return bubblesort(arr)


def run_bubblesort_local(arr):
    results = [bubblesort(arr) for _ in range(os.cpu_count())]
    return results


def run_bubblesort_remote(arr):
    results = ray.get([bubble_sort_distributed.remote(arr) for _ in range(os.cpu_count())])
    return results


array = [random.randint(1, 100) for _ in range(10_000)]

# local bubblesort
local_result = bubblesort(array)
print(local_result)


# remote bubblesort
bubblesort_id = bubble_sort_distributed.remote(array)
remote_result = ray.get(bubblesort_id)
print(remote_result)


# parallel remote
print("remote bubblesort run")
cProfile.run("run_bubblesort_remote(array)")


# parallel local
print('local bubblesort run')
cProfile.run("run_bubblesort_local(array)")

ray.shutdown()
