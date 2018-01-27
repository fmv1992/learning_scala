"""Plot the profile of our current FunctionI function.

Data was obtained by:
`scala ./modular_inverses.scala > data.txt`

Referent to code of './modular_inverses.scala' in:
Git commit:
commit ec30f27a3b76186c33e42ee7b79088ea65626c68

"""
import numpy as np

import matplotlib.pyplot as plt

import seaborn as sns


def main():
    """Execute plotting."""
    # Load data.
    with open('./modular_inverses_data.txt', 'rt') as f:
        data = f.readlines()

    # Skip the first data point which comes from an assertion.
    data_points = data[1:]
    y, x = zip(*map(lambda x: list(map(int, x.split(':'))), data_points))
    x = np.array(x)
    y = np.array(y)
    y = y - y.min()

    # Instantiate figure.
    fig, ax = plt.subplots()
    # Execute the plot.
    sns.regplot(x, y, ax=ax, order=2, ci=95, fit_reg=True)
    ax.set_xlabel('input N to FunctionI')
    ax.set_ylabel('Wall time when N was computed (ms)')

    # import ipdb; ipdb.set_trace()  # XXX BREAKPOINT
    fig.tight_layout(rect=[0, 0.03, 1, 0.95])

    fig.savefig('modular_inverses_time_from_commit_ec30f27.png',
                dpi=500)


if __name__ == '__main__':
    main()
