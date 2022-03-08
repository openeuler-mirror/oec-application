import csv
import pathlib
import ast

conclusion_mapping = {
    1: 'version changed',
    2: 'missing',
    3: 'check needed',
    4: 'package name changed',
    5: 'version unchanged'
}
conclusionType_mapping = {
    1: 'Missing function',
    2: 'Function return value type changed',
    3: 'The function parameter size changes',
    4: 'The number of function parameters changes'
}

fieldnames = ['runtime', 'softwareToBeEvaluated', 'systemArchitecture', 'migrationExplain', 'assessmentItem',
              'dependentCount', 'packageChangeCount', 'packageDefectCount', 'externalInterfacesCount',
              'interfaceChangeCount', 'interfaceDefectCount', 'compatibilityInterfacePercent',
              'compatibilityDependentPercent', 'firstName', 'secondName', 'comparedOSDependence',
              'sub_comparedOSDependence', 'openEulerDependence', 'sub_openEulerDependence',
              'directDependenceConclusionType', 'conclusion', 'functionName', 'applications', 'currentOS', 'targetOS',
              'currentFunction', 'targetFunction', 'currentFileName', 'targetFileName', 'currentLib', 'targetLib',
              'Change', 'conclusionType', 'jarName', 'compatibility', 'package', 'class', 'methods', 'change']

with open('result.csv', 'w', newline='', encoding='utf-8') as f:
    writer = csv.DictWriter(f, fieldnames)
    writer.writeheader()

    html_dir = pathlib.Path('.')
    for html_file in html_dir.iterdir():
        if html_file.suffix != '.html':
            continue
        html_path = html_file
        with open(html_path, 'r', encoding='utf-8') as f:
            lines = f.readlines()
            for line in lines:
                if line.strip().startswith("data: {\'baseInfo\'"):
                    data = ast.literal_eval(line.strip()[6:])[0]
                    break
        # csv_name = str(html_path.with_suffix('.csv'))
        # reset all field with -
        row = {}
        for fieldname in fieldnames:
            row[fieldname] = '-'

        # update base info and count info
        baseInfo = data['baseInfo']
        row.update(baseInfo)
        countInfo = data['countInfo']
        row.update(countInfo)
        directDependenceBothOfName = data['directDependenceBothOfName']
        row.update(directDependenceBothOfName)

        # update direct dependence
        directDependenceTable = data['directDependenceTable']
        for directDependence in directDependenceTable:
            row['comparedOSDependence'] = directDependence["comparedOSDependence"]
            row['openEulerDependence'] = directDependence["openEulerDependence"]
            row['directDependenceConclusionType'] = conclusion_mapping[directDependence['conclusionType']]
            children = directDependence['children']
            for sub_item in children:
                row['sub_comparedOSDependence'] = sub_item["comparedOSDependence"]
                row['sub_openEulerDependence'] = sub_item["openEulerDependence"]
                row['conclusion'] = sub_item["conclusion"]
                writer.writerow(row)

        # reset direct dependence
        row['comparedOSDependence'] = row['openEulerDependence'] = row['conclusion'] = '-'
        row['sub_comparedOSDependence'] = row['sub_openEulerDependence'] = row['directDependenceConclusionType'] = '-'

        # update function interface
        functionInterfaceTable = data['functionInterfaceTable']
        for functionInterface in functionInterfaceTable:
            row['functionName'] = functionInterface['functionName']
            row['applications'] = functionInterface['applications']
            row['conclusionType'] = conclusionType_mapping[functionInterface['conclusionType']]
            children = functionInterface['children']
            current_function = children[0]
            row['currentOS'] = current_function['OS']
            row['currentFunction'] = current_function['function']
            row['currentFileName'] = current_function['fileName']
            row['currentLib'] = current_function['lib']
            row['Change'] = current_function['change']
            target_function = children[1]
            row['targetOS'] = target_function['OS']
            row['targetFunction'] = target_function['function']
            row['targetFileName'] = target_function['fileName']
            row['targetLib'] = target_function['lib']
            writer.writerow(row)

        # reset function interface
        row['functionName'] = row['applications'] = row['conclusionType'] = row['Change'] = '-'
        row['currentOS'] = row['currentFunction'] = row['currentFileName'] = row['currentLib'] = '-'
        row['targetOS'] = row['targetFunction'] = row['targetFileName'] = row['targetLib'] = '-'

        # update jar methods
        jarMethodsRemovedTable = data['jarMethodsRemovedTable']
        for jarMethod in jarMethodsRemovedTable:
            row['jarName'] = jarMethod['jarName']
            row['compatibility'] = jarMethod['compatibility']
            children = jarMethod['children']
            for sub_item in children:
                row['package'] = sub_item['package']
                row['class'] = sub_item['class']
                row['methods'] = sub_item['methods']
                row['change'] = sub_item['change']
                writer.writerow(row)

        # add separator
        for fieldname in fieldnames:
            row[fieldname] = '-'
        writer.writerow(row)
