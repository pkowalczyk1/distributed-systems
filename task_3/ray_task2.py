import random
import logging
import ray

if ray.is_initialized:
    ray.shutdown()
ray.init(address='ray://172.19.0.2:10001', ignore_reinit_error=True, logging_level=logging.ERROR)


@ray.remote
def process_list(arr):
    return sum(arr) / len(arr)


@ray.remote
def process_dict(dictionary):
    return max(dictionary, key=dictionary.get)


lists = [[random.randint(1, 100) for _ in range(100_000)] for _ in range(3)]
dicts = [{"value1": 15, "value2": 20}, {"value3": 30, "value4": 50}]

list_refs = [ray.put(el) for el in lists]
dict_refs = [ray.put(el) for el in dicts]

print(f"Average values in lists: {ray.get([process_list.remote(el) for el in list_refs])}")
print(f"Max value keys in dicts: {ray.get([process_dict.remote(el) for el in dict_refs])}")

ray.shutdown()
