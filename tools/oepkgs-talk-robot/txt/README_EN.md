# Automated Building of Question Pairs

This document and the md_read.py script are compiled to facilitate expansion and MD problem generation.

## Built Question Pair Instances

```sh
{
    "que_ans": [
        {
            "id": 0,
            "question": "example question",
            "answer": "id_0.txt"
        }
    ],
    "count": 0
}
```

## How to Build

First, go to the related path.

```sh
cd oec-application/tools/oepkgs-talk-robot/txt
```

Run **md_read.py** to add the document to the content. The relationship between the document title and content must be clear. For example, if an existing `rpm.md` document needs to be built, run the following command:

```sh
python md_read.py --input rpm.md
```

In this way, the expansion of the question pair is completed.
