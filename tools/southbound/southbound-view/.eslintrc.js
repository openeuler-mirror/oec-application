module.exports = {
    "env": {
        "browser": true,
        "es2021": true,
        "commonjs": true
    },
    "extends": [
        "eslint:recommended",
        "plugin:vue/essential"
    ],
    "parserOptions": {
        "ecmaVersion": 12,
        "sourceType": "module"
    },
    "plugins": [
        "vue"
    ],
    "globals": {
        "Atomics": "readonly",
        "SharedArrayBuffer": "readonly",
        "process": true
    },
    "rules": {
        'indent': [2, 2],
        'semi': [2, 'always'],
        'quotes': [2, 'single'],
        'eqeqeq': [2, 'always'],
        'no-undef': 2,
        'no-mixed-spaces-and-tabs': [2],
        'no-extra-semi': [2],
        'comma-dangle': [2, 'never'],
        'no-var': 2,
        'no-with': 2,
        'vue/no-use-v-if-with-v-for': 2,
        'camelcase': [0, {'properties': 'never'}],
        'nonwords': [0],
        'use-isnan': 2
    }
};
