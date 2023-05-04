import ray
import random
import math
import time
import logging


@ray.remote
class CalculationProgressActor:
    def __init__(self, samples_num: int):
        self.samples_num = samples_num
        self.samples_done_per_task = {}

    def update_progress(self, task_id: int, samples_done: int) -> None:
        self.samples_done_per_task[task_id] = samples_done

    def get_calculation_progress(self) -> float:
        return sum(self.samples_done_per_task.values()) / self.samples_num


@ray.remote
def calculation_task(samples_num: int, task_id: int, reporting_actor: ray.actor.ActorHandle) -> int:
    counter = 0
    for i in range(samples_num):
        x, y = random.uniform(-1, 1), random.uniform(-1, 1)
        if math.hypot(x, y) <= 1:
            counter += 1
        if (i + 1) % 10_000 == 0:
            reporting_actor.update_progress.remote(task_id, i + 1)

    reporting_actor.update_progress.remote(task_id, samples_num)
    return counter


if ray.is_initialized:
    ray.shutdown()
ray.init(address='ray://172.19.0.2:10001', ignore_reinit_error=True, logging_level=logging.ERROR)

calculation_tasks = 20
samples_per_task = 1_000_000
progress_actor = CalculationProgressActor.remote(calculation_tasks * samples_per_task)

results = [calculation_task.remote(samples_per_task, i, progress_actor) for i in range(calculation_tasks)]

while True:
    current_progress = ray.get(progress_actor.get_calculation_progress.remote())
    print(f"Progress: {int(current_progress * 100)}%")
    if current_progress == 1:
        break
    time.sleep(0.5)

final_results = ray.get(results)
pi = (sum(final_results) / (calculation_tasks * samples_per_task)) * 4
print(f"Pi: {pi}")

ray.shutdown()
