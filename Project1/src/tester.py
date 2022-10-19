import subprocess
import difflib
from tqdm import tqdm

subprocess.run(['javac', '*.java'])
result = open('result.txt','w')
overall = 0
result.write(f"Total unmatching input count: {overall}")
result.write("\n First set")
for i in tqdm(range(1, 11)):
    with open(f'inputs/input{i}.txt') as input:
        subprocess.run(['java','Project1',f"inputs/input{i}.txt",f'res/outputs/output{i}.txt'])
    with open(f'res/outputs/output{i}.txt') as output:
        with open(f'outputs/output{i}.txt') as real_output:
            result.write(f"\nInput{i}\n")
            diff = difflib.unified_diff(real_output.readlines(), output.readlines())
            count = 0
            for line in diff:
                result.write(line)
                count += 1
            if count > 0:
                result.write(f"\nUnmatching found\n")
                overall += 1

result.write("\n Second set")
for i in tqdm(range(1, 51)):
    with open(f'inputs2/input{i}.txt') as input:
        subprocess.run(['java','Project1',f"inputs2/input{i}.txt",f'res/outputs2/output{i}.txt'])
    with open(f'res/outputs2/output{i}.txt') as output:
        with open(f'outputs2/output{i}.txt') as real_output:
            result.write(f"\nInput{i}\n")
            diff = difflib.unified_diff(real_output.readlines(), output.readlines())
            count = 0
            for line in diff:
                result.write(line)
                count += 1
            if count > 0:
                result.write(f"\nUnmatching found\n")
                overall += 1

result.write("\n Third set")
for i in tqdm(range(0, 100)):
    with open(f'inputs3/input{i}.txt') as input:
        subprocess.run(['java','Project1',f"inputs3/input{i}.txt",f'res/outputs3/output{i}.txt'])
    with open(f'res/outputs3/output{i}.txt') as output:
        with open(f'outputs3/output{i}.txt') as real_output:
            result.write(f"\nInput{i}\n")
            diff = difflib.unified_diff(real_output.readlines(), output.readlines())
            count = 0
            for line in diff:
                result.write(line)
                count += 1
            if count > 0:
                result.write(f"\nUnmatching found\n")
                overall += 1


result.seek(0)
result.write(f"Total unmatching input count: {overall}")
if overall == 0:
    print("Good to go")
else:
    print(f"Total unmatching input count: {overall}")