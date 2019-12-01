/*
 * Copyright (C) 2019. Mikhail Kulesh
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details. You should have received a copy of the GNU General
 * Public License along with this program.
 */

import "package:flutter/material.dart";

import "../constants/Dimens.dart";
import "../constants/Drawables.dart";
import "../constants/Strings.dart";
import "../iscp/StateManager.dart";
import "../iscp/messages/AmpOperationCommandMsg.dart";
import "../utils/Logging.dart";
import "../widgets/CustomDivider.dart";
import "../widgets/CustomImageButton.dart";
import "../widgets/CustomTextLabel.dart";
import "UpdatableView.dart";

class AmplifierControlView extends UpdatableView
{
    static const List<String> UPDATE_TRIGGERS = [
        StateManager.CONNECTION_EVENT
    ];

    AmplifierControlView(final ViewContext viewContext) : super(viewContext, UPDATE_TRIGGERS);

    @override
    Widget createView(BuildContext context, VoidCallback updateCallback)
    {
        Logging.info(this, "rebuild widget");

        final Widget image = Padding(
            padding: ActivityDimens.coverImagePadding(context),
            child: ConstrainedBox(
                constraints: BoxConstraints(maxHeight: ControlViewDimens.imageHeight),
                child: Image.asset(Drawables.amplifier)
            )
        );

        return Column(
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: [
                CustomTextLabel.small(Strings.remote_interface_amp),
                CustomDivider(),
                image,
                _buildTable()
            ]);
    }

    Widget _buildTable()
    {
        final Map<int, TableColumnWidth> columnWidths = Map();
        columnWidths[0] = FlexColumnWidth();
        columnWidths[1] = IntrinsicColumnWidth();
        columnWidths[2] = IntrinsicColumnWidth();
        columnWidths[3] = IntrinsicColumnWidth();
        columnWidths[4] = FlexColumnWidth();

        // Top rows: quick menu, setup, return
        final List<TableRow> rows = List();
        rows.add(TableRow(children: [
            SizedBox.shrink(),
            CustomTextLabel.small(Strings.remote_interface_power, textAlign: TextAlign.center),
            CustomTextLabel.small(Strings.remote_interface_input, textAlign: TextAlign.center),
            CustomTextLabel.small(Strings.remote_interface_volume, textAlign: TextAlign.center),
            SizedBox.shrink()
        ]));
        rows.add(TableRow(children: [
            SizedBox.shrink(),
            _buildBtn(AmpOperationCommandMsg.output(AmpOperationCommand.PWRTG)),
            Row(
                mainAxisSize: MainAxisSize.min,
                children: [
                    _buildBtn(AmpOperationCommandMsg.output(AmpOperationCommand.SLIDOWN)),
                    _buildBtn(AmpOperationCommandMsg.output(AmpOperationCommand.SLIUP))
                ],
            ),
            Row(
                mainAxisSize: MainAxisSize.min,
                children: [
                    _buildBtn(AmpOperationCommandMsg.output(AmpOperationCommand.AMTTG)),
                    _buildBtn(AmpOperationCommandMsg.output(AmpOperationCommand.MVLDOWN)),
                    _buildBtn(AmpOperationCommandMsg.output(AmpOperationCommand.MVLUP))
                ],
            ),
            SizedBox.shrink()
        ]));

        return Table(
            columnWidths: columnWidths,
            defaultVerticalAlignment: TableCellVerticalAlignment.middle,
            children: rows,
        );
    }

    Widget _buildBtn(final AmpOperationCommandMsg cmd)
    {
        return CustomImageButton.normal(
            cmd.getValue.icon,
            cmd.getValue.description,
            padding: ControlViewDimens.imgButtonPadding,
            onPressed: ()
            => stateManager.sendMessage(cmd),
            isEnabled: stateManager.isConnected
        );
    }
}